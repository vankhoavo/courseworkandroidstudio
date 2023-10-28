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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.util.Calendar;

public class UpdateHike extends AppCompatActivity {
    private EditText hike_name_edit, hike_location_edit, hike_description_edit, hike_length_edit;
    private RadioGroup radioGroup_edit;
    private RadioButton radio_yes_edit, radio_no_edit;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnerHike;
    private Button hike_datetime_edit, hike_update;
    private AlertDialog.Builder alertDialog;
    private ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);

        setInit();
        try {
            getandsettoUpdateHike();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setListenerUpdateHike();
        dateupdate();
    }

    public void setInit() {
        hike_update = findViewById(R.id.hike_update);
        hike_datetime_edit = findViewById(R.id.hike_datetime_edit);

        hike_name_edit = findViewById(R.id.hike_name_edit);
        hike_location_edit = findViewById(R.id.hike_location_edit);
        hike_description_edit = findViewById(R.id.hike_description_edit);
        hike_length_edit = findViewById(R.id.hike_length_edit);

        radioGroup_edit = findViewById(R.id.radioGroup_edit);
        radio_yes_edit = findViewById(R.id.radio_yes_edit);
        radio_no_edit = findViewById(R.id.radio_no_edit);

        spinnerHike = findViewById(R.id.hike_spinner_edit);

        alertDialog = new AlertDialog.Builder(this);

        icon_back = findViewById(R.id.icon_back);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isupdate() {
        if (hike_name_edit.getText().toString().trim().isEmpty()) {
            showToast("Enter name of update hike");
            return false;
        } else if (hike_location_edit.getText().toString().trim().isEmpty()) {
            showToast("Enter location of updatehike");
            return false;
        } else if (radioGroup_edit.getCheckedRadioButtonId() == -1) {
            showToast("Please tick parking available");
            return false;
        } else if (hike_length_edit.getText().toString().trim().isEmpty()) {
            showToast("Enter length of update hike");
            return false;
        } else if (hike_description_edit.getText().toString().trim().isEmpty()) {
            showToast("Enter description of update hike");
            return false;
        }
        return true;
    }

    public void setListenerUpdateHike() {
        icon_back.setOnClickListener(v -> {
            startActivity(new Intent(UpdateHike.this, MainActivity.class));
        });
        hike_update.setOnClickListener(v -> {
            if (isupdate()) {
                int requireGroupUpdate = radioGroup_edit.getCheckedRadioButtonId();
                RadioButton radioGroup_edit = findViewById(requireGroupUpdate);
                alertDialog.setTitle("Confirmation Update")
                        .setMessage("Name: " + hike_name_edit.getText().toString().trim() + "\n" +
                                "Location: " + hike_location_edit.getText().toString().trim() + "\n" +
                                "Date: " + hike_datetime_edit.getText().toString().trim() + "\n" +
                                "Parking available: " + radioGroup_edit.getText().toString().trim() + "\n" +
                                "Length: " + hike_length_edit.getText().toString().trim() + "\n" +
                                "Level: " + spinnerHike.getSelectedItem().toString().trim() + "\n" +
                                "Description: " + hike_description_edit.getText().toString().trim())
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateAllHike();
                                startActivity(new Intent(UpdateHike.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    public void updateAllHike() {
        MyDatabaseHelper mydbofHike = new MyDatabaseHelper(UpdateHike.this);
        HikeModel hikeModel = new HikeModel();
        int requireGroupUpdate = radioGroup_edit.getCheckedRadioButtonId();
        RadioButton radioGroup_edit = findViewById(requireGroupUpdate);
        hikeModel.setHike_id(Integer.parseInt(getIntent().getStringExtra("hike_id")));
        hikeModel.setHike_name(hike_name_edit.getText().toString().trim());
        hikeModel.setHike_location(hike_location_edit.getText().toString().trim());
        hikeModel.setHike_parking_available(radioGroup_edit.getText().toString().trim());
        hikeModel.setHike_datetime(hike_datetime_edit.getText().toString().trim());
        hikeModel.setHike_length(hike_length_edit.getText().toString().trim());
        //hikeModel.setHike_parking_available(spinnerHike.getSelectedItem().toString().trim()); cái cũ
        hikeModel.setHike_difficulty(spinnerHike.getSelectedItem().toString().trim()); //cái mới đúng không? ->
        hikeModel.setHike_description(hike_description_edit.getText().toString().trim());
        mydbofHike.updateHike(hikeModel);
    }

    public void getandsettoUpdateHike() throws ParseException {
        if (getIntent().hasExtra("hike_name") &&
                getIntent().hasExtra("hike_location") &&
                getIntent().hasExtra("hike_datetime") &&
                getIntent().hasExtra("hike_parking_available") &&
                getIntent().hasExtra("hike_length") &&
                getIntent().hasExtra("hike_difficulty") &&
                getIntent().hasExtra("hike_description")) {
            hike_datetime_edit.setText((String) getIntent().getSerializableExtra("hike_datetime"));
            hike_name_edit.setText((String) getIntent().getSerializableExtra("hike_name"));
            hike_location_edit.setText((String) getIntent().getSerializableExtra("hike_location"));
            hike_description_edit.setText((String) getIntent().getSerializableExtra("hike_description"));
            hike_length_edit.setText((String) getIntent().getSerializableExtra("hike_length"));

            String radioButton = getIntent().getStringExtra("hike_parking_available");
            if (radioButton.equals("Yes")) {
                radio_yes_edit.setChecked(true);
                radio_no_edit.setChecked(false);
            } else {
                radio_yes_edit.setChecked(false);
                radio_no_edit.setChecked(true);
            }

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hike_level, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinnerHike.setAdapter(adapter);

            String selectionHike = (String) getIntent().getSerializableExtra("hike_difficulty");
            int spinnerButton = adapter.getPosition(selectionHike);
            spinnerHike.setSelection(spinnerButton);
        }
    }

    public void openDatePiker(View view) {
        datePickerDialog.show();
    }

    private void dateupdate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month += 1;
                String dateupdate = dateToString(day, month, year);
                hike_datetime_edit.setText(dateupdate);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String dateToString(int day, int month, int year) {
        if (day < 10) {
            return month + "/0" + day + "/" + year;
        } else if (month < 10) {
            return "0" + month + "/" + day + "/" + year;
        } else if (day < 10 && month < 10) {
            return "0" + month + "/0" + day + "/" + year;
        }
        return month + "/" + day + "/" + year;
    }
}