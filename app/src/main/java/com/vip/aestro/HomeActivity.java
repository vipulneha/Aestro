package com.vip.aestro;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private TextInputEditText firstName, surname, placeOfBirth, state;
    private TextView dateOfBirth, timeOfBirth;
    private Spinner countrySpinner;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Views
        firstName = findViewById(R.id.firstName);
        surname = findViewById(R.id.surname);
        placeOfBirth = findViewById(R.id.placeOfBirth);
        state = findViewById(R.id.state);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        timeOfBirth = findViewById(R.id.timeOfBirth);
        countrySpinner = findViewById(R.id.countrySpinner);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up DatePicker
        dateOfBirth.setOnClickListener(v -> showDatePicker());

        // Set up TimePicker
        timeOfBirth.setOnClickListener(v -> showTimePicker());

        // Set up Spinner (Countries)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);

        // Set default selection to India (make sure India is in the array)
        countrySpinner.setSelection(adapter.getPosition("India"));

        // Button Submit action
        btnSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                // Passing form data to the new activity
                Intent intent = new Intent(HomeActivity.this, AestroDetailActivity.class);
                intent.putExtra("firstName", firstName.getText().toString());
                intent.putExtra("surname", surname.getText().toString());
                intent.putExtra("dateOfBirth", dateOfBirth.getText().toString());
                intent.putExtra("timeOfBirth", timeOfBirth.getText().toString());
                intent.putExtra("placeOfBirth", placeOfBirth.getText().toString());
                intent.putExtra("state", state.getText().toString());
                intent.putExtra("country", countrySpinner.getSelectedItem().toString());
                startActivity(intent);
            } else {
                Toast.makeText(HomeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show Date Picker
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(HomeActivity.this, (view, year1, month1, dayOfMonth) ->
                dateOfBirth.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1), year, month, day);
        datePicker.show();
    }

    // Show Time Picker
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(HomeActivity.this, (view, hourOfDay, minute1) ->
                timeOfBirth.setText(hourOfDay + ":" + minute1), hour, minute, true);
        timePicker.show();
    }

    // Validate the form
    private boolean validateForm() {
        return !firstName.getText().toString().isEmpty() &&
                !surname.getText().toString().isEmpty() && !dateOfBirth.getText().toString().isEmpty() &&
                !timeOfBirth.getText().toString().isEmpty() && !placeOfBirth.getText().toString().isEmpty() &&
                !state.getText().toString().isEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.termsandcondition:
                startActivity(new Intent(HomeActivity.this, TermsConditionActivity.class));
                return true;
            case R.id.privacypolicy:
                startActivity(new Intent(HomeActivity.this, PrivacyPolicyActivity.class));
                return true;
            case R.id.other:
                startActivity(new Intent(HomeActivity.this, OtherActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
