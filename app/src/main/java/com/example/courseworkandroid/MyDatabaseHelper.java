package com.example.courseworkandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //Context: Là một lớp trong Android chứa thông tin về trạng thái và môi trường của ứng dụng.
    // Một Context thường được sử dụng để có được thông tin về các tài nguyên của ứng dụng, khởi chạy các dịch vụ,
    // và thực hiện các hoạt động liên quan đến môi trường thực thi của ứng dụng.
    private Context context;
    private static final String DATABASE_NAME = "DB_Hike.db";
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

    public void addHike(HikeModel hikeModel) {
        SQLiteDatabase hike = this.getWritableDatabase();
        ContentValues cvalues = new ContentValues();
        cvalues.put("hike_name", hikeModel.getHike_name());
        cvalues.put("hike_location", hikeModel.getHike_location());
        cvalues.put("hike_datetime", hikeModel.getHike_datetime());
        cvalues.put("hike_parking_available", hikeModel.getHike_parking_available());
        cvalues.put("hike_length", hikeModel.getHike_length());
        cvalues.put("hike_difficulty", hikeModel.getHike_difficulty());
        cvalues.put("hike_description", hikeModel.getHike_description());
        long rs = hike.insert(TABLE_NAME, null, cvalues);
        if (rs == -1) {
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful to Add", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readdatabaseHike() {
        String queryhike = "SELECT hike_id, hike_name, hike_location, hike_datetime, hike_parking_available, hike_length, hike_difficulty, hike_description FROM " + TABLE_NAME;
        SQLiteDatabase hike = this.getWritableDatabase();
        Cursor cursor = null;
        if (hike != null) {
            cursor = hike.rawQuery(queryhike, null);
        }
        return cursor;
    }

    public void deleteHike1Row(String row_id) {
        SQLiteDatabase myhikedb = this.getWritableDatabase();
        long rs = myhikedb.delete(TABLE_NAME, "hike_id=?", new String[]{row_id});
        if (rs == -1) {
            Toast.makeText(context, "Failed to Delete of Hike", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful to Delete of Hike ", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateHike(HikeModel hikeModel) {
        SQLiteDatabase hikeupdate = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hike_name", hikeModel.getHike_name());
        contentValues.put("hike_location", hikeModel.getHike_location());
        contentValues.put("hike_datetime", hikeModel.getHike_datetime());
        contentValues.put("hike_parking_available", hikeModel.getHike_parking_available());
        contentValues.put("hike_length", hikeModel.getHike_length());
        contentValues.put("hike_difficulty", hikeModel.getHike_difficulty());
        contentValues.put("hike_description", hikeModel.getHike_description());
        long rs = hikeupdate.update(TABLE_NAME, contentValues, "hike_id=?", new String[]{String.valueOf(hikeModel.getHike_id())});
        if (rs == -1) {
            Toast.makeText(context, "Failed to Update Hike", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful to Update Hike", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllHike() {
        SQLiteDatabase deleteallhike = this.getWritableDatabase();
        long rs = deleteallhike.delete(TABLE_NAME, null, null);
        if (rs == -1) {
            Toast.makeText(context, "Failed to Delete All Hike", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful to Delete All Hike", Toast.LENGTH_SHORT).show();
        }
    }
}
