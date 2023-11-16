package com.example.courseworkandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class AddHike extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private EditText hike_name, hike_location, hike_length, hike_description;
    private DatePickerDialog datePickerDialog;
    private RadioGroup radio_group;
    private RadioButton radio_yes, radio_no;
    private Button hike_datetime, save;
    private Spinner hike_spinner;
    private AlertDialog.Builder alertDiglog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);

        setInit();
        setListener();
        setNavigationView();
    }

    public void setInit() {

        hike_name = findViewById(R.id.hike_name);
        hike_location = findViewById(R.id.hike_location);
        hike_length = findViewById(R.id.hike_length);
        hike_description = findViewById(R.id.hike_description);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.add_hike);

        hike_datetime = findViewById(R.id.hike_datetime);
        hike_datetime.setText(getDate());

        radio_group = findViewById(R.id.radioGroup);
        radio_yes = findViewById(R.id.radio_yes);
        radio_no = findViewById(R.id.radio_no);

        hike_spinner = findViewById(R.id.hike_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hike_level, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        hike_spinner.setAdapter(adapter);

        alertDiglog = new AlertDialog.Builder(this);
        save = findViewById(R.id.save);
    }

    public void setListener() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String dateStr = dateToString(day, month, year);
                hike_datetime.setText(dateStr);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        save.setOnClickListener(v -> {
            if (isValidAddHike()) {
                int groupbutton = radio_group.getCheckedRadioButtonId();
                RadioButton radio_group = findViewById(groupbutton);
                alertDiglog.setTitle("Confimation")
                        .setMessage("Name: " + hike_name.getText().toString().trim() + "\n" +
                                "Location: " + hike_location.getText().toString().trim() + "\n" +
                                "Date: " + hike_datetime.getText().toString().trim() + "\n" +
                                "Parking available: " + radio_group.getText().toString().trim() + "\n" +
                                "Length: " + hike_length.getText().toString().trim() + "\n" +
                                "Level: " + hike_spinner.getSelectedItem().toString().trim() + "\n" +
                                "Description: " + hike_description.getText().toString().trim())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                addHike();
                                startActivity(new Intent(AddHike.this, MainActivity.class));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
    }

    public void addHike() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddHike.this);
        HikeModel hikeModel = new HikeModel();
        int groupbutton = radio_group.getCheckedRadioButtonId();
        RadioButton radio_group = findViewById(groupbutton);
        hikeModel.setHike_name(hike_name.getText().toString().trim());
        hikeModel.setHike_location(hike_location.getText().toString().trim());
        hikeModel.setHike_datetime(hike_datetime.getText().toString().trim());
        hikeModel.setHike_length(hike_length.getText().toString().trim());
        hikeModel.setHike_parking_available(radio_group.getText().toString().trim());
        hikeModel.setHike_description(hike_description.getText().toString().trim());
        hikeModel.setHike_difficulty(hike_spinner.getSelectedItem().toString().trim());
        myDatabaseHelper.addHike(hikeModel);
    }

    private String dateToString(int day, int month, int year) {
        if (day < 10) {
            return month + "/0" + day + "/" + year;
        } else if (month < 10) {
            return "0" + month + "/" + day + "/" + year;
        } else if (day < 10 && month < 10) {
            return "0" + month + "/0" + day + "/" + year;
        }
        return day + "/" + month + "/" + year;
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return dateToString(day, month, year);
    }

    public void openDatePiker(View view)
    {
        datePickerDialog.show();
    }

    public void setNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.add_hike) {
                    return true;
                } else if (itemId == R.id.home_hike) {
                    startActivity(new Intent(AddHike.this, MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidAddHike() {
        if (hike_name.getText().toString().trim().isEmpty()) {
            showToast("Enter name of hike");
            return false;
        } else if (hike_location.getText().toString().trim().isEmpty()) {
            showToast("Enter location of hike");
            return false;
        } else if (hike_length.getText().toString().trim().isEmpty()) {
            showToast("Enter length of hike");
            return false;
        } else if (hike_description.getText().toString().trim().isEmpty()) {
            showToast("Enter description of hike");
            return false;
        } else if (radio_group.getCheckedRadioButtonId() == -1) {
            showToast("Please tick parking available");
            return false;
        }
        return true;
    }
}