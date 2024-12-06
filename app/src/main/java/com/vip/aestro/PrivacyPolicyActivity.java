package com.vip.aestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        TextView termsTextView = findViewById(R.id.privacyTextView);
        termsTextView.setText(loadTextFromFile());
    }

    private String loadTextFromFile() {
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.privacypolicy);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}