package com.bahydev.bahyantivirus;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchAutoScan;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchRootCheck;
    private Button btnSaveSettings;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "AppPreferences";
    private static final String KEY_AUTO_SCAN = "autoScanEnabled";
    private static final String KEY_ROOT_CHECK = "rootCheckEnabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize SharedPreferences to save the settings
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize UI elements
        switchAutoScan = findViewById(R.id.switchAutoScan);
        switchRootCheck = findViewById(R.id.switchRootCheck);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // Load saved settings from SharedPreferences
        loadSettings();

        // Set listeners for the "Save Settings" button
        btnSaveSettings.setOnClickListener(v -> {
            // Retrieve the state of the switches
            boolean isAutoScanEnabled = switchAutoScan.isChecked();
            boolean isRootCheckEnabled = switchRootCheck.isChecked();

            // Save the settings to SharedPreferences
            saveSettings(isAutoScanEnabled, isRootCheckEnabled);

            // Show confirmation message
            Toast.makeText(SettingsActivity.this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();

            // Optionally, you can close this activity and return to the main screen
            finish();  // Closes the settings activity
        });
    }

    /**
     * Load the settings from SharedPreferences and update the switches
     */
    private void loadSettings() {
        boolean isAutoScanEnabled = sharedPreferences.getBoolean(KEY_AUTO_SCAN, false);
        boolean isRootCheckEnabled = sharedPreferences.getBoolean(KEY_ROOT_CHECK, false);

        // Set the state of the switches based on saved preferences
        switchAutoScan.setChecked(isAutoScanEnabled);
        switchRootCheck.setChecked(isRootCheckEnabled);
    }

    /**
     * Save the settings to SharedPreferences
     */
    private void saveSettings(boolean autoScanEnabled, boolean rootCheckEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_AUTO_SCAN, autoScanEnabled);
        editor.putBoolean(KEY_ROOT_CHECK, rootCheckEnabled);
        editor.apply(); // Apply the changes asynchronously
    }

    /**
     * Simulate Auto-Scan functionality (this would be a background task in a real app)
     */
    private void simulateAutoScan(boolean enabled) {
        if (enabled) {
            // In a real app, you would start a background task to scan for malware
            Toast.makeText(this, "Auto-Scan Enabled. Scanning in the background...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Auto-Scan Disabled.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Simulate Root Check functionality (this would check if the device is rooted)
     */
    private void simulateRootCheck(boolean enabled) {
        if (enabled) {
            // In a real app, you would check if the device is rooted and show the result
            boolean isRooted = checkIfDeviceIsRooted();
            String rootStatus = isRooted ? "Root access detected!" : "No root access detected.";
            Toast.makeText(this, rootStatus, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Root Check Disabled.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A simple method to simulate checking if the device is rooted.
     * In a real-world scenario, you would use a library or method to detect root.
     */
    private boolean checkIfDeviceIsRooted() {
        // For simulation, we return true (indicating the device is rooted)
        // In a real app, implement actual root detection logic
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load the settings each time the activity is resumed
        loadSettings();
    }
}