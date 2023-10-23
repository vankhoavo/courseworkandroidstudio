package com.example.courseworkandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private MyDatabaseHelper databaseHelper;
    private List<HikeModel> myhike = new ArrayList<>();
    private AdapterHike adapterHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
        setNavigationView();

        databaseHelper = new MyDatabaseHelper(MainActivity.this);
        displayDataHike();
        adapterHike = new AdapterHike(MainActivity.this, this, myhike);
        recyclerView.setAdapter(adapterHike);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void setInit() {
        recyclerView = findViewById(R.id.recyclerview_hike);
        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setSelectedItemId(R.id.home_hike);
    }

    public void displayDataHike() {
        Cursor cursorHike = databaseHelper.readdatabaseHike();
        if (cursorHike.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Data Hike", Toast.LENGTH_SHORT).show();
        } else {
            while (cursorHike.moveToNext()) {
                myhike.add(new HikeModel(Integer.parseInt(cursorHike.getString(0)),
                        cursorHike.getString(1),
                        cursorHike.getString(2),
                        cursorHike.getString(3),
                        cursorHike.getString(4),
                        cursorHike.getString(5),
                        cursorHike.getString(6),
                        cursorHike.getString(7)));
            }
        }
    }

    public void setNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_hike) {
                    return true;
                } else if (itemId == R.id.add_hike) {
                    startActivity(new Intent(MainActivity.this, AddHike.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }
}