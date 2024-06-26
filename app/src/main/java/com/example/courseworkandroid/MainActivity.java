package com.example.courseworkandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
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
    private AlertDialog.Builder alert;
    private ImageView hike_delete_all;

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

        alert = new AlertDialog.Builder(this);
        hike_delete_all.setOnClickListener(v -> {
            alert.setTitle("Clear all Hike")
                    .setMessage("Do you want to be sure to delete all Hike?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseHelper.deleteAllHike();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        });
    }

    public void setInit() {
        hike_delete_all = findViewById(R.id.hike_delete_all);
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