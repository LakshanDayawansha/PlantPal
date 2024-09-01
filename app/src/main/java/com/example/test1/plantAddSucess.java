package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class plantAddSucess extends AppCompatActivity {

    Map<String, Object> data;
    TextView textView;
    Button button;
    Date date,date1;
    Timestamp timestamp,timestamp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_add_sucess);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userPlantsCollection = db.collection("users").document("userid").collection("user plants");
        data = (Map<String, Object>) getIntent().getSerializableExtra("data");
        date = (Date) getIntent().getSerializableExtra("date");
        date1 = (Date) getIntent().getSerializableExtra("date1");
        timestamp = new Timestamp(date);
        timestamp1 = new Timestamp(date1);
        data.put("last watered",timestamp);
        data.put("last fertilized",timestamp1);
        textView = findViewById(R.id.addsucesstext);
        button = findViewById(R.id.addsucessbtn);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), myPlants.class);
                startActivity(intent);
            }
        });




       userPlantsCollection.add(data)
                .addOnSuccessListener(documentReference -> {
                    // Data has been successfully added with this documentReference
                    String documentId = documentReference.getId();
                    Toast.makeText(plantAddSucess.this, "Plant added sucessfully.", Toast.LENGTH_SHORT).show();
                    textView.setText("plant added sucessfully!");
                    
                    // You can use the documentId to identify the added data if needed.
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occur during the operation
                    Toast.makeText(plantAddSucess.this, "Failed to add plant: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // Log the exception for debugging
                    e.printStackTrace();
                });


    }
    @Override
    public void onBackPressed() {
        Toast.makeText(plantAddSucess.this, "Press ok", Toast.LENGTH_SHORT).show();
    }
}