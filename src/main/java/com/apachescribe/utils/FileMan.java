package com.apachescribe.utils;

import java.io.File;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// @Component
public class FileMan {

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
        System.out.println(fileName);
        System.out.println(path);
        return filePath;
    }

    // input: dirName, output: void: create the directory
    public void createDirIfNotExist(String directoryName) {
        File file = new File(directoryName);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory does not exist but is needed, creating it... ");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    // input: dir, output: String[] fileNames
    public File[] getFiles(String dir) {
        File[] listOfFiles = null;
        try {
            createDirIfNotExist(dir);
            System.out.println("Examining directory: " + dir);
            File directory = new File(dir);
            System.out.println("Directory found. Proceeding to examine. ");
            listOfFiles = directory.listFiles();
            Integer numberOfFile = listOfFiles.length;
            if (numberOfFile == 0) {
                System.out.println("The directory may be empty");
            }

        } catch (Exception e) {
            System.out.println("Error occured: " + e);
        }
        return listOfFiles;
    }
}