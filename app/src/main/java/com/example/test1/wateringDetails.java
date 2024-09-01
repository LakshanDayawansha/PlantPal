package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class wateringDetails extends AppCompatActivity {

    TextView waterText1,waterText2,waterText3,waterText4,waterText5,waterText6;
    FirebaseFirestore db;
    Map<String, Object> data;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watering_details);

        waterText1 = findViewById(R.id.water1);
        waterText2 = findViewById(R.id.water2);
        waterText3 = findViewById(R.id.water3);
        waterText4 = findViewById(R.id.water4);
        waterText5 = findViewById(R.id.water5);
        waterText6 = findViewById(R.id.water6);



        data = (Map<String, Object>) getIntent().getSerializableExtra("data");
        //Toast.makeText(wateringDetails.this, data.get("name")+"Plant", Toast.LENGTH_SHORT).show();

        db = FirebaseFirestore.getInstance();
        //docId = getIntent().getStringExtra("docId");
    }



    public void onTextViewClick(View view){
        int id = view.getId();
        Intent intent = new Intent(getApplicationContext(), frtilizingDetails.class);
        intent.putExtra("data", (Serializable) data);
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        calendar.setTime(currentDate);

        if (id == R.id.water1) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.water2) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.water3) {
            calendar.add(Calendar.DAY_OF_YEAR, 2);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.water4) {
            calendar.add(Calendar.DAY_OF_YEAR, 3);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.water5) {
            calendar.add(Calendar.DAY_OF_YEAR, 4);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.water6) {
            calendar.add(Calendar.DAY_OF_YEAR, 5);
            intent.putExtra("date",(Date) calendar.getTime());
            startActivity(intent);
        }


    }




}