package com.example.courseworkandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterHike extends RecyclerView.Adapter<AdapterHike.MyViewr> {
    //    Biến context được sử dụng để lấy thông tin liên quan đến môi trường của ứng dụng, như tài nguyên, dịch vụ hệ thống, v.v.
    private Context context;
    //    Biến activity là một tham chiếu đến hoạt động (activity) chứa adapter. Activity thường được sử dụng để thực hiện các hành động cụ thể liên quan đến giao diện người dùng.
    private Activity activity;
    //    Biến myhike là một danh sách các đối tượng HikeModel. Đây có thể là dữ liệu mà adapter sẽ hiển thị trong giao diện người dùng.
    private List<HikeModel> myhike;

    public AdapterHike(Activity activity, Context context, List<HikeModel> myhike) {
        this.activity = activity;
        this.context = context;
        this.myhike = myhike;
    }

    @NonNull
    @Override
    public AdapterHike.MyViewr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_hike, parent, false);
        return new MyViewr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHike.MyViewr holder, int position) {
        HikeModel hikeModel = myhike.get(position);

        // Set data to views
        holder.hike_name_view.setText(String.valueOf(hikeModel.getHike_name()));
        holder.hike_date_view.setText(String.valueOf(hikeModel.getHike_datetime()));

        // Edit action
        holder.icon_image_edit.setOnClickListener(v -> startUpdateActivity(hikeModel));

        // View details action
        holder.main_view.setOnClickListener(v -> startDetailActivity(hikeModel));

        // Remove action
        holder.icon_image_remove.setOnClickListener(v -> showDeleteConfirmationDialog(hikeModel));
    }

    private void startUpdateActivity(HikeModel hikeModel) {
        Intent intent = new Intent(context, UpdateHike.class);
        putHikeModelExtras(intent, hikeModel);
        activity.startActivityForResult(intent, 1);
    }

    private void startDetailActivity(HikeModel hikeModel) {
        Intent intent = new Intent(context, DetailHike.class);
        putHikeModelExtras(intent, hikeModel);
        activity.startActivityForResult(intent, 1);
    }

    private void putHikeModelExtras(Intent intent, HikeModel hikeModel) {
        intent.putExtra("hike_id", String.valueOf(hikeModel.getHike_id()));
        intent.putExtra("hike_name", String.valueOf(hikeModel.getHike_name()));
        intent.putExtra("hike_location", String.valueOf(hikeModel.getHike_location()));
        intent.putExtra("hike_datetime", String.valueOf(hikeModel.getHike_datetime()));
        intent.putExtra("hike_parking_available", String.valueOf(hikeModel.getHike_parking_available()));
        intent.putExtra("hike_length", String.valueOf(hikeModel.getHike_length()));
        intent.putExtra("hike_difficulty", String.valueOf(hikeModel.getHike_difficulty()));
        intent.putExtra("hike_description", String.valueOf(hikeModel.getHike_description()));
    }

    private void showDeleteConfirmationDialog(HikeModel hikeModel) {
        new AlertDialog.Builder(context)
                .setTitle("Delete " + String.valueOf(hikeModel.getHike_name()))
                .setMessage("Do you want to delete the hike with this name " + String.valueOf(hikeModel.getHike_name()) + "?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteHike1Row(hikeModel.getHike_id());
                    activity.startActivityForResult(new Intent(context, MainActivity.class), 1);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .show();
    }

    public void deleteHike1Row(int hike_id) {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
        myDatabaseHelper.deleteHike1Row(String.valueOf(hike_id));
    }

    @Override
    public int getItemCount() {
        return myhike.size();
    }

    public class MyViewr extends RecyclerView.ViewHolder {
        private TextView hike_name_view, hike_date_view;
        private ImageView icon_image_remove, icon_image_edit;
        private LinearLayout main_view;
        private AlertDialog.Builder alerDialog;

        public MyViewr(@NonNull View itemView) {
            super(itemView);
            hike_date_view = itemView.findViewById(R.id.hike_date_view);
            hike_name_view = itemView.findViewById(R.id.hike_name_view);
            icon_image_remove = itemView.findViewById(R.id.icon_image_remove);
            icon_image_edit = itemView.findViewById(R.id.icon_image_edit);
            main_view = itemView.findViewById(R.id.main_view);
            alerDialog = new AlertDialog.Builder(context);
        }
    }
}
