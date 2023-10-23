package com.example.courseworkandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class UpdateHike extends AppCompatActivity {

    private EditText hike_name_edit, hike_location_edit, hike_description_edit, hike_length_edit;
    private RadioGroup radioGroup_edit;
    private RadioButton radio_yes_edit, radio_no_edit;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnerHike;
    private Button hike_datetime_edit, hike_update;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);

        setInit();
    }

    public void setInit() {
        hike_datetime_edit = findViewById(R.id.hike_datetime_edit);
        hike_update = findViewById(R.id.hike_update);

        hike_name_edit = findViewById(R.id.hike_name_edit);
        hike_location_edit = findViewById(R.id.hike_location_edit);
        hike_description_edit = findViewById(R.id.hike_description_edit);
        hike_length_edit = findViewById(R.id.hike_location_edit);

        radioGroup_edit = findViewById(R.id.radioGroup_edit);
        radio_yes_edit = findViewById(R.id.radio_yes_edit);
        radio_no_edit = findViewById(R.id.radio_no_edit);

        spinnerHike = findViewById(R.id.hike_spinner_edit);
    }
}