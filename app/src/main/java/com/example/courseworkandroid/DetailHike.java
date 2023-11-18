package com.example.courseworkandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;

public class DetailHike extends AppCompatActivity {
    private ImageView icon_back;
    private TextView back, value_name_hike, value_location_hike, value_datetime_hike, value_parkingavailable_hike, value_length_hike, value_difficulty_hike, value_description_hike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hike);

        setInit();
        setListener();

        try {
            getsethikedata();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void setInit() {
        back = findViewById(R.id.back);
        icon_back = findViewById(R.id.icon_back);
        value_name_hike = findViewById(R.id.value_name_hike);
        value_location_hike = findViewById(R.id.value_location_hike);
        value_datetime_hike = findViewById(R.id.value_datetime_hike);
        value_parkingavailable_hike = findViewById(R.id.value_parkingavailable_hike);
        value_length_hike = findViewById(R.id.value_length_hike);
        value_difficulty_hike = findViewById(R.id.value_difficulty_hike);
        value_description_hike = findViewById(R.id.value_description_hike);
    }

    private void setListener() {
        back.setOnClickListener(v -> startMainActivity());
        icon_back.setOnClickListener(v -> startMainActivity());
    }

    private void startMainActivity() {
        Intent intent = new Intent(DetailHike.this, MainActivity.class);
        startActivity(intent);
    }


    public void getsethikedata() throws ParseException {
        Intent intent = getIntent();

        if (intent.hasExtra("hike_name") &&
                intent.hasExtra("hike_location") &&
                intent.hasExtra("hike_datetime") &&
                intent.hasExtra("hike_length") &&
                intent.hasExtra("hike_parking_available") &&
                intent.hasExtra("hike_difficulty") &&
                intent.hasExtra("hike_description")) {

            value_name_hike.setText(intent.getStringExtra("hike_name"));
            value_location_hike.setText(intent.getStringExtra("hike_location"));
            value_datetime_hike.setText(intent.getStringExtra("hike_datetime"));
            value_length_hike.setText(intent.getStringExtra("hike_length"));
            value_difficulty_hike.setText(intent.getStringExtra("hike_difficulty"));
            value_description_hike.setText(intent.getStringExtra("hike_description"));
            value_parkingavailable_hike.setText(intent.getStringExtra("hike_parking_available"));
        }
    }

}