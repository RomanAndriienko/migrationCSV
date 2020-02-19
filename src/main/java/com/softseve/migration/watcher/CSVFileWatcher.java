package com.softseve.migration.watcher;

import java.io.File;

public class CSVFileWatcher implements Watcher {
    private String directoryPath = "path";

    @Override
    public File watch() {
        return null;
    }

//    @Override
//    public File watch() {
//        File[] files = new File(directoryPath).listFiles();
//
//        for(File f : files){
//            String filePath = f.getAbsolutePath();
//            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
//            if("csv".equals(fileExtension)){
//                System.out.println("CSV file found -> " + filePath);
//                // Call the method checkForCobalt(filePath);
//            }
//        }
    }
