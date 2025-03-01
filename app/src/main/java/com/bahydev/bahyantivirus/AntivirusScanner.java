package com.bahydev.bahyantivirus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AntivirusScanner {

    // Check for suspicious file extensions
    public boolean isSuspicious(File file) {
        String[] suspiciousExtensions = {".apk", ".exe", ".bat", ".jar"};
        String fileName = file.getName().toLowerCase();

        // Check if file matches any suspicious extensions
        for (String ext : suspiciousExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Scan files in a directory
    public List<File> scanDirectory(String directoryPath) {
        List<File> suspiciousFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        // Check if directory exists and is readable
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isSuspicious(file)) {
                        suspiciousFiles.add(file);
                    }
                }
            }
        }
        return suspiciousFiles;
    }
}
