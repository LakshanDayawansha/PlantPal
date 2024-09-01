package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class myPlantTasks extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plant_tasks);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String Name = getIntent().getStringExtra("name");

        ImageView imageView = findViewById(R.id.task_image);
        //TextView addPlantText = findViewById(R.id.ta);

        Glide.with(this).load(imageUrl).into(imageView);

    }







}