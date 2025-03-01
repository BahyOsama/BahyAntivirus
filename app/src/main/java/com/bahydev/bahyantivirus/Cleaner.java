package com.bahydev.bahyantivirus;

import android.content.Context;

import java.io.File;

public class Cleaner {

    // Clear app's cache directory
    public void clearAppCache(Context context) {
        File cacheDir = context.getCacheDir();
        deleteFiles(cacheDir);
    }

    // Clear external storage cache if applicable
    public void clearExternalCache(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            deleteFiles(externalCacheDir);
        }
    }

    // Clear all cache in the app's cache directory and external cache
    public void clearAllCache(MainActivity context) {
        clearAppCache(context);
        clearExternalCache(context);
    }

    // Recursive method to delete files in a directory
    private void deleteFiles(File dir) {
        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFiles(file);  // Recursively delete files in subdirectories
                    } else {
                        file.delete();  // Delete individual files
                    }
                }
            }
        }
    }
}