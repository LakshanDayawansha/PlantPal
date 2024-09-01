package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseFirestore db;
    GridAdapter gridAdapter;

    ArrayList<String> plantName = new ArrayList<>();
    ArrayList<String> plantImages = new ArrayList<>();



    ArrayList<Map<String, Object>> plantData = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        gridAdapter = new GridAdapter(MainActivity.this,plantName,plantImages);
        binding.Grid.setAdapter(gridAdapter);


        db.collection("plants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the data from the document
                            String name = document.getString("name");
                            String image = document.getString("image");
                            Number water = document.getDouble("water");
                            Map<String, Object> data = document.getData();



                            // Add the data to your arrays
                            plantName.add(name);
                            plantImages.add(image);
                            plantData.add(data);
                        }

                        gridAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                        Toast.makeText(MainActivity.this,"Error occured",Toast.LENGTH_SHORT).show();
                    }
                });




        binding.Grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"You Clicked on "+ plantName.get(i),Toast.LENGTH_SHORT).show();




                Intent intent = new Intent(getApplicationContext(), selectPlants.class);
                intent.putExtra("imageUrl", plantImages.get(i));
                intent.putExtra("name", plantName.get(i));
                intent.putExtra("data", (Serializable) plantData.get(i));
                startActivity(intent);


            }
        });

    }
}