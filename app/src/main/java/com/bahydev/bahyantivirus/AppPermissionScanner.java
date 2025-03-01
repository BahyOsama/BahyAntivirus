package com.bahydev.bahyantivirus;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppPermissionScanner {

    private Context context;

    public AppPermissionScanner(MainActivity context) {
        this.context = context;
    }

    // Scan installed apps for suspicious permissions
    public List<String> scanInstalledAppsForPermissions() {
        List<String> suspiciousApps = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : installedApps) {
            String[] permissions;
            try {
                permissions = context.getPackageManager().getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;

                if (permissions != null) {
                    for (String permission : permissions) {
                        if (Arrays.asList(SuspiciousPermissions.SUSPICIOUS_PERMISSIONS).contains(permission)) {
                            suspiciousApps.add(app.loadLabel(pm).toString());
                            break;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return suspiciousApps;
    }
}