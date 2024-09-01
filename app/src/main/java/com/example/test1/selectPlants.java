package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class selectPlants extends AppCompatActivity {

    Button addPlantBtn;
    String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_plants);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String Name = getIntent().getStringExtra("name");
        Map<String, Object> data = (Map<String, Object>) getIntent().getSerializableExtra("data");


        ImageView imageView = findViewById(R.id.img);
        TextView addPlantText = findViewById(R.id.addPlantText);
        addPlantBtn = findViewById(R.id.addplant);


        Glide.with(this).load(imageUrl).into(imageView);
        addPlantText.setText(Name);

        addPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), wateringDetails.class);
                intent.putExtra("data", (Serializable) data);
                CollectionReference userPlantsCollection = db.collection("users").document("userid").collection("user plants");

                //Map<String, Object> plantData = new HashMap<>();
                //plantData.put("name", Name);
               //plantData.put("image", imageUrl);
                //plantData.put("water",water);

                // Check if the item already exists before adding it
                userPlantsCollection.whereEqualTo("name", data.get("name"))
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                boolean itemAlreadyExists = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // If there is a document with the same name, it already exists
                                    itemAlreadyExists = true;
                                    break;
                                }
                                if (!itemAlreadyExists) {
                                    // If the item doesn't exist, add it
                                    startActivity(intent);
                                } else {
                                    // Display a message indicating that the item already exists
                                    Toast.makeText(selectPlants.this, data.get("name")+"Plant already exists.", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                // Handle the query error
                                Log.e("Firestore", "Error querying for existing plants: " + task.getException().getMessage());
                            }
                        });











            }
        });

    }
}