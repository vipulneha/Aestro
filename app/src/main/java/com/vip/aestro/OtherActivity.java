package com.vip.aestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {
    private TextView tvRateUs, tvShareApp, tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        // Initialize Views
        tvRateUs = findViewById(R.id.tvRateUs);
        tvShareApp = findViewById(R.id.tvShareApp);
        tvAppVersion = findViewById(R.id.tvAppVersion);

        // Set app version dynamically
        tvAppVersion.setText("Version: " + getAppVersion());

        // Set click listeners
        tvRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        tvShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
    }

    // Get the app version dynamically
    private String getAppVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    // Open the app's Play Store page for rating
    private void rateApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open Play Store", Toast.LENGTH_SHORT).show();
        }
    }

    // Share the app link with others
    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this amazing app: https://play.google.com/store/apps/details?id=" + getPackageName();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share App via"));
    }
}