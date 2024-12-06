package com.vip.aestro;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AestroDetailActivity extends AppCompatActivity {

    private TextView firstNameView, fatherNameView, surnameView, dateOfBirthView, timeOfBirthView, placeOfBirthView, stateView, countryView;
    private TextView titleView;
    private TextView aestroDetail;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_ASTRO = "aestro";
    private static final String KEY_NAME = "savedName";
    private static final long ONE_DAY_IN_MILLIS = 86400000;
    private Button coppyAestro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aestro_detail);

        // Initialize views
        firstNameView = findViewById(R.id.firstNameView);
        titleView = findViewById(R.id.titleView);
        surnameView = findViewById(R.id.surnameView);
        dateOfBirthView = findViewById(R.id.dateOfBirthView);
        timeOfBirthView = findViewById(R.id.timeOfBirthView);
        placeOfBirthView = findViewById(R.id.placeOfBirthView);
        stateView = findViewById(R.id.stateView);
        countryView = findViewById(R.id.countryView);
        aestroDetail = findViewById(R.id.aestroDetail);
        coppyAestro = findViewById(R.id.coppyAestro);


        Button btnShareWhatsApp = findViewById(R.id.btnShareWhatsApp);
        Button btnShareInstagram = findViewById(R.id.btnShareInstagram);
        Button btnShareLinkedIn = findViewById(R.id.btnShareLinkedIn);
        Button btnShareOther = findViewById(R.id.btnShareOther);
        Button btnShareFacebook = findViewById(R.id.btnFacebook);

        // Get the data from Intent
        String firstName = getIntent().getStringExtra("firstName");
        String surname = getIntent().getStringExtra("surname");
        String dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        String timeOfBirth = getIntent().getStringExtra("timeOfBirth");
        String placeOfBirth = getIntent().getStringExtra("placeOfBirth");
        String state = getIntent().getStringExtra("state");
        String country = getIntent().getStringExtra("country");

        // Full name combination to use as a key for SharedPreferences
        String fullName = firstName + " " + surname;

        String[] aestroArray = getResources().getStringArray(R.array.aestro_array); // Replace with your actual string array resource name

        // Get SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        long lastSavedTime = sharedPreferences.getLong(KEY_NAME + "_time", 0);
        String savedName = sharedPreferences.getString(KEY_NAME, null);
        String savedAestro = sharedPreferences.getString(KEY_ASTRO, null);

        if (savedName == null || !savedName.equals(fullName) || System.currentTimeMillis() - lastSavedTime > ONE_DAY_IN_MILLIS) {
            // If name has changed or it's been more than one day, generate a new aestro detail
            Random random = new Random();
            int randomIndex = random.nextInt(aestroArray.length);
            savedAestro = aestroArray[randomIndex];

            // Save the new name, aestro, and the current time in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, fullName);
            editor.putString(KEY_ASTRO, savedAestro);
            editor.putLong(KEY_NAME + "_time", System.currentTimeMillis());
            editor.apply();
        }

        // Set the data to TextViews
        titleView.setText(firstName + " " + surname);
        firstNameView.setText(firstName);
        surnameView.setText(surname);
        dateOfBirthView.setText("Birth Date:- " + dateOfBirth);
        timeOfBirthView.setText("Birth Time:- " + timeOfBirth);
        placeOfBirthView.setText("Birth Place:- " + placeOfBirth);
        stateView.setText("Birth State:- " + state);
        countryView.setText("Country:- " + country);
        aestroDetail.setText(savedAestro);

        coppyAestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyTextToClipboard();
            }
        });

        btnShareWhatsApp.setOnClickListener(v -> shareText("com.whatsapp"));
        btnShareInstagram.setOnClickListener(v -> shareText("com.instagram.android"));
        btnShareLinkedIn.setOnClickListener(v -> shareText("com.linkedin.android"));
        btnShareFacebook.setOnClickListener(v -> shareText("com.facebook.katana"));
        btnShareOther.setOnClickListener(v -> shareText(null));
    }

    private void copyTextToClipboard() {
        // Get the text from the TextView
        String textToCopy = aestroDetail.getText().toString();

        // Get the Clipboard Manager
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a ClipData object to hold the text
        ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);

        // Set the ClipData to the Clipboard Manager
        clipboard.setPrimaryClip(clip);

        // Show a confirmation message
        Toast.makeText(getApplicationContext(), "Aestrology is copied", Toast.LENGTH_SHORT).show();
    }

    private void shareText(String packageName) {
        String astrologyText = aestroDetail.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "My todays Aestrology is: " + astrologyText);

        if (packageName != null) {
            shareIntent.setPackage(packageName);
        }

        try {
            startActivity(shareIntent);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error or show a message if app is not installed
        }
    }

}