package com.bahydev.bahyantivirus;

import java.io.File;

public class RootDetection {

    // Check if the device is rooted
    public boolean isDeviceRooted() {
        String[] paths = {"/system/xbin/su", "/system/app/Superuser.apk", "/system/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }
        return false;
    }
}