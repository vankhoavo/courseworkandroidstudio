package com.example.courseworkandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddHike extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private EditText hike_name, hike_location, hike_length, hike_description;
    private DatePickerDialog datePickerDialog;
    private RadioGroup radio_group;
    private Button hike_datetime, save;
    private Spinner hike_spinner;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);
        initializeViews();
        setListeners();
        setNavigationView();
    }

    private void initializeViews() {
        hike_name = findViewById(R.id.hike_name);
        hike_location = findViewById(R.id.hike_location);
        hike_length = findViewById(R.id.hike_length);
        hike_description = findViewById(R.id.hike_description);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.add_hike);

        hike_datetime = findViewById(R.id.hike_datetime);
        hike_datetime.setText(getFormattedDate());

        radio_group = findViewById(R.id.radioGroup);

        hike_spinner = findViewById(R.id.hike_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hike_level, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        hike_spinner.setAdapter(adapter);

        alertDialogBuilder = new AlertDialog.Builder(this);
        save = findViewById(R.id.save);
    }

    private void setListeners() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month += 1;
            String dateStr = getFormattedDate(day, month, year);
            hike_datetime.setText(dateStr);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        save.setOnClickListener(v -> {
            if (isValidAddHike()) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        int groupButtonId = radio_group.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(groupButtonId);

        alertDialogBuilder.setTitle("Confirmation")
                .setMessage(String.format(
                        "Name: %s\nLocation: %s\nDate: %s\nParking available: %s\nLength: %s\nLevel: %s\nDescription: %s",
                        hike_name.getText().toString().trim(),
                        hike_location.getText().toString().trim(),
                        hike_datetime.getText().toString().trim(),
                        selectedRadioButton.getText().toString().trim(),
                        hike_length.getText().toString().trim(),
                        hike_spinner.getSelectedItem().toString().trim(),
                        hike_description.getText().toString().trim()))
                .setPositiveButton("Yes Hike", (dialog, i) -> {
                    addHike();
                    startActivity(new Intent(AddHike.this, MainActivity.class));
                })
                .setNegativeButton("Cancel Hike", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }

    private void addHike() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddHike.this);
        HikeModel hikeModel = createHikeModel();
        myDatabaseHelper.addHike(hikeModel);
    }

    private HikeModel createHikeModel() {
        int groupButtonId = radio_group.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(groupButtonId);

        HikeModel hikeModel = new HikeModel();
        hikeModel.setHike_name(hike_name.getText().toString().trim());
        hikeModel.setHike_location(hike_location.getText().toString().trim());
        hikeModel.setHike_datetime(hike_datetime.getText().toString().trim());
        hikeModel.setHike_length(hike_length.getText().toString().trim());
        hikeModel.setHike_parking_available(selectedRadioButton.getText().toString().trim());
        hikeModel.setHike_description(hike_description.getText().toString().trim());
        hikeModel.setHike_difficulty(hike_spinner.getSelectedItem().toString().trim());

        return hikeModel;
    }

    private String getFormattedDate(int day, int month, int year) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return dateFormat.format(calendar.getTime());
    }

    private String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        return getFormattedDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    public void openDatePiker(View view) {
        datePickerDialog.show();
    }

    private void setNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.add_hike) {
                return true;
            } else if (itemId == R.id.home_hike) {
                startActivity(new Intent(AddHike.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidAddHike() {
        if (hike_name.getText().toString().trim().isEmpty()) {
            showToast("Enter the name of the hike");
            return false;
        } else if (hike_location.getText().toString().trim().isEmpty()) {
            showToast("Enter the location of the hike");
            return false;
        } else if (hike_length.getText().toString().trim().isEmpty()) {
            showToast("Enter the length of the hike");
            return false;
        } else if (hike_description.getText().toString().trim().isEmpty()) {
            showToast("Enter the description of the hike");
            return false;
        } else if (radio_group.getCheckedRadioButtonId() == -1) {
            showToast("Please select parking availability");
            return false;
        }
        return true;
    }
}
