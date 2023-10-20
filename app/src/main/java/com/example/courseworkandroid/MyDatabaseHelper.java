package com.example.courseworkandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //Nó lỗi là hãy tạo constructor cho nó
    //Context: Là một lớp trong Android chứa thông tin về trạng thái và môi trường của ứng dụng.
    // Một Context thường được sử dụng để có được thông tin về các tài nguyên của ứng dụng, khởi chạy các dịch vụ,
    // và thực hiện các hoạt động liên quan đến môi trường thực thi của ứng dụng.
    private Context context;
    private static final String DATABASE_NAME = "DB_Hike";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "hike";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase hike) {
        hike.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(hike_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "hike_name TEXT NOT NULL, " +
                        "hike_location TEXT NOT NULL, " +
                        "hike_datetime TEXT NOT NULL, " +
                        "hike_parking_available TEXT NOT NULL, " +
                        "hike_length INTEGER NOT NULL, " +
                        "hike_difficulty TEXT NOT NULL, " +
                        "hike_description TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase hike, int oldVersion, int newVersion) {
        hike.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(hike);
    }
}
