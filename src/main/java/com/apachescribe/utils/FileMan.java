package com.apachescribe.utils;

import java.io.File;

import org.apache.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// @Component
public class FileMan {

    private static final Logger log = Logger.getLogger(FileMan.class);

    // input: fullFilePath, output: filename
    public String getFilenameFromPath(String path) {
        String fileName = null;
        fileName = path.split("/")[path.split("/").length - 1];
        return fileName;
    }

    // input: fullFilePath, output: path without file name
    public String getFilePath(String path) {
        String fileName = getFilenameFromPath(path);
        String filePath = path.replace(fileName, "");
        log.info(fileName);
        log.info(path);
        return filePath;
    }

    // input: dirName, output: void: create the directory
    public void createDirIfNotExist(String directoryName) {
        File file = new File(directoryName);
        if (!file.exists()) {
            if (file.mkdir()) {
                log.info("Directory does not exist but is needed, creating it... ");
            } else {
                log.warn("Failed to create directory!");
            }
        }
    }

    // input: dir, output: String[] fileNames
    public File[] getFiles(String dir) {
        File[] listOfFiles = null;
        try {
            createDirIfNotExist(dir);
            log.info("Examining directory: " + dir);
            File directory = new File(dir);
            log.info("Directory found. Proceeding to examine. ");
            listOfFiles = directory.listFiles();
            Integer numberOfFile = listOfFiles.length;
            if (numberOfFile == 0) {
                log.info("The directory may be empty");
            }

        } catch (Exception e) {
            log.error("Error occured: " + e);
        }
        return listOfFiles;
    }
}