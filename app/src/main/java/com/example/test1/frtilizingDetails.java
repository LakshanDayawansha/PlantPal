package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class frtilizingDetails extends AppCompatActivity {

    TextView fertiText1,fertiText2,fertiText3,fertiText4,fertiText5,fertiText6;
    Map<String, Object> data;
    Calendar calendar;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frtilizing_details);

        fertiText1 = findViewById(R.id.ferti1);
        fertiText2 = findViewById(R.id.ferti2);
        fertiText3 = findViewById(R.id.ferti3);
        fertiText4 = findViewById(R.id.ferti4);
        fertiText5 = findViewById(R.id.ferti5);
        fertiText6 = findViewById(R.id.ferti6);


        data = (Map<String, Object>) getIntent().getSerializableExtra("data");
        date = (Date) getIntent().getSerializableExtra("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Toast.makeText(frtilizingDetails.this, sdf.format(date.getTime())+"Plant", Toast.LENGTH_SHORT).show();
        //Toast.makeText(frtilizingDetails.this, data.get("name")+"Plant", Toast.LENGTH_SHORT).show();
    }


    public void onTextViewClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(getApplicationContext(), plantAddSucess.class);
        intent.putExtra("data", (Serializable) data);
        intent.putExtra("date",date);
        calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.setTime(currentDate);


        if (id == R.id.ferti1) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.ferti2) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.ferti3) {
            calendar.add(Calendar.DAY_OF_YEAR, 2);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.ferti4) {
            calendar.add(Calendar.DAY_OF_YEAR, 3);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.ferti5) {
            calendar.add(Calendar.DAY_OF_YEAR, 4);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        } else if (id == R.id.ferti6) {
            calendar.add(Calendar.DAY_OF_YEAR, 5);
            intent.putExtra("date1",(Date) calendar.getTime());
            startActivity(intent);
        }
    }



}