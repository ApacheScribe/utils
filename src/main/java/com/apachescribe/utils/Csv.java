package com.apachescribe.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import com.opencsv.CSVWriter;

import org.apache.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
public class Csv {

    private static final Logger log = Logger.getLogger(Csv.class);

    // @Value("${csvFileRelativePath}")
    public String csvFileRelativePath;

    // @Value("${csvFileArchiveToMailRelativePath}")
    public String csvFileArchiveToMailRelativePath;

    // @Value("${csvFileArchiveArchivedRelativePath}")
    public String csvFileArchiveArchivedRelativePath;

    // @Value("${sftp-destination}")
    public String sftp_destination;

    // @Autowired
    private Utils utils;

    // @Autowired
    private FileMan fileMan;

    // @Autowired
    private Email email;

    // @Autowired
    private SFTPClient sftpClient;

    private static FileWriter output = null;
    private static CSVWriter write = null;

    private File createFile(String name) {
        File file = null;
        try {
            log.info("Locating csv file ... ");
            file = new File(csvFileRelativePath);
        } catch (Exception e) {
            log.error(e);
        }
        return file;
    }

    public void openWriter(File csvFile) {

        String firstLine = null;

        try {
            log.info("Initializing csv writer ... ");
            output = new FileWriter(csvFile);
            write = new CSVWriter(output, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] header = { "Business Transaction", "Amount", "Text", "Cust Code", "Business Area", "Profit Center",
                    "Posting Date", "Document Date" };
            write.writeNext(header);

            // firstLine = Files.lines(csvFile.toPath()).findFirst().get();

        } catch (Exception e) {
            log.info("Initializing csv writer failed: " + e);
        }
    }

    public void closeWriter() {
        try {
            log.info("Closing csv writer");
            write.close();
        } catch (Exception e) {
            log.info("Closing csv writer failed: " + e);
            e.printStackTrace();
        }
    }

    public void writeToCsvFile(String msisdn, String id, String amount, String string, String string2, String string3,
            String string4, String string5) {
        File csvFile = new File(this.csvFileRelativePath);
        try {
            if (!csvFile.exists()) {
                log.info("Creating new csv file ... ");
                csvFile = createFile(
                        this.csvFileRelativePath.split("/")[this.csvFileRelativePath.split("/").length - 1]);
            } else {
                log.info("Appending to existing csv file ... ");
            }
            String[] value = { msisdn, id, amount, string, string2, string3, string4, string5 };
            write.writeNext(value);
            // write.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void archiveExistingCsv(String filename) {
        fileMan.createDirIfNotExist(csvFileArchiveArchivedRelativePath);
        File toArchiveFile = new File(csvFileArchiveToMailRelativePath + filename);
        toArchiveFile.renameTo(new File(csvFileArchiveArchivedRelativePath + filename));
    }

    public File[] getFilesToMail() {
        File[] filesToMail = null;
        try {
            filesToMail = fileMan.getFiles(csvFileArchiveToMailRelativePath);
            log.info("Found " + Integer.toString(filesToMail.length) + " files");
        } catch (Exception e) {
            log.error("Error finding files ... ");
        }
        return filesToMail;
    }

    public void stageFile(String filename) {
        fileMan.createDirIfNotExist(csvFileArchiveToMailRelativePath);
        String newFileName = Utils.filenameTimestamp(new Date());
        File oldFile = new File(filename);
        oldFile.renameTo(new File(csvFileArchiveToMailRelativePath + newFileName));
    }

    public void uploadCsv(String filename) {
        sftpClient.upload(filename, sftp_destination);
    }

    public void sendEmailAndArchive() {
        File[] files = getFilesToMail();
        for (int i = 0; i < files.length; i++) {
            File var = files[i];
            uploadCsv(files[i].getAbsolutePath()); // upload to sftp server
            email.sendMailWithAttachment(files, ".csv"); // this won't work, check semantics
            log.info("Notification email sent for file " + var.getName() + ". Archiving it ...");
            try {
                archiveExistingCsv(var.getName());
                log.info("File " + var.getName() + " archived successfully....");
            } catch (Exception e) {
                log.info("File " + var.getName() + " archived failed with error ...." + e);
            }
        }
    }
}