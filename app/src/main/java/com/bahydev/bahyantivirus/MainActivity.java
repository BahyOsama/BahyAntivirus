package com.bahydev.bahyantivirus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cleaner cleaner;
    private RootDetection rootDetection;
    private AppPermissionScanner permissionScanner;

    private TextView statusTextView;
    private Button btnScan, btnRootCheck, btnCleanCache, btnSettings;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the components
        cleaner = new Cleaner();
        rootDetection = new RootDetection();
        permissionScanner = new AppPermissionScanner(this);

        // UI elements
        statusTextView = findViewById(R.id.statusTextView);
        btnScan = findViewById(R.id.btnScan);
        btnRootCheck = findViewById(R.id.btnRootCheck);
        btnCleanCache = findViewById(R.id.btnCleanCache);
        btnSettings = findViewById(R.id.btnSettings);

        // Scan for malware button
        btnScan.setOnClickListener(v -> {
            List<File> suspiciousFiles = new AntivirusScanner().scanDirectory("/storage/emulated/0");
            // Display results
            String result = "Suspicious Files Found: \n";
            for (File file : suspiciousFiles) {
                result += file.getName() + "\n";
            }
            statusTextView.setText(result.isEmpty() ? "No suspicious files found" : result);
        });

        // Root check button
        btnRootCheck.setOnClickListener(v -> {
            boolean isRooted = rootDetection.isDeviceRooted();
            String rootStatus = isRooted ? "Root access detected" : "No root access";
            statusTextView.setText(rootStatus);
        });

        // Clean cache button
        btnCleanCache.setOnClickListener(v -> {
            cleaner.clearAllCache(this);
            Toast.makeText(this, "Cache cleaned!", Toast.LENGTH_SHORT).show();
        });

        // Settings button
        btnSettings.setOnClickListener(v -> {
            // Navigate to settings activity or fragment
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Check for suspicious permissions
        checkForSuspiciousPermissions();
    }

    /**
     * Scans installed apps and checks for suspicious permissions
     */
    private void checkForSuspiciousPermissions() {
        List<String> suspiciousApps = permissionScanner.scanInstalledAppsForPermissions();

        if (!suspiciousApps.isEmpty()) {
            String result = "Suspicious Apps Found:\n";
            for (String app : suspiciousApps) {
                result += app + "\n";
            }
            statusTextView.setText(result);
        } else {
            statusTextView.setText("No suspicious apps detected");
        }
    }
}