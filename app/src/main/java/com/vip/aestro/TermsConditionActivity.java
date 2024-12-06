package com.vip.aestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TermsConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        TextView termsTextView = findViewById(R.id.termsTextView);
        termsTextView.setText(loadTextFromFile());
    }

    private String loadTextFromFile() {
        StringBuilder text = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.termsandcondition);
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