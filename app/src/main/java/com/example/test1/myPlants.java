package com.example.test1;



import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.test1.databinding.ActivityMainBinding;
import com.example.test1.databinding.ActivityMyPlantsBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class myPlants extends AppCompatActivity {

    private static final String CHANNEL_ID = "plant_tasks";
    ActivityMyPlantsBinding myPlantsbinding;
    FirebaseFirestore myPlantsdb;
    MyPlantsGridAdapter myPlantsGridAdapter;

    ArrayList<String> myplantName = new ArrayList<>();
    ArrayList<String> myplantImages = new ArrayList<>();
    ArrayList<String> myplantID = new ArrayList<>();
    ArrayList<Long> myplantWater = new ArrayList<>();
    ArrayList<Long> myplantWaterNotifications = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPlantsbinding = ActivityMyPlantsBinding.inflate(getLayoutInflater());
        setContentView(myPlantsbinding.getRoot());



        myPlantsdb = FirebaseFirestore.getInstance();
        myPlantsGridAdapter = new MyPlantsGridAdapter(myPlants.this,myplantName,myplantImages,myplantID);
        myPlantsbinding.plantsGridView.setAdapter(myPlantsGridAdapter);
        View myplantyGridItemLayout = getLayoutInflater().inflate(R.layout.my_plants_griditem, null);
        ImageView dots = myplantyGridItemLayout.findViewById(R.id.dots);


        createNotificationChannel();
        scheduleNotifications();

        myPlantsdb.collection("users")
                .document("userid")
                .collection("user plants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String documentId = document.getId();
                            // Get the data from the document
                            String name = document.getString("name");
                            String image = document.getString("image");
                            Long water = document.getLong("water");

                            // Add the data to your arrays
                            myplantName.add(name);
                            myplantImages.add(image);
                            myplantID.add(documentId);
                            myplantWater.add(water);


                        }

                        myPlantsGridAdapter.notifyDataSetChanged();
                    } else {
                        // Handle errors
                        Toast.makeText(myPlants.this,"Error occurred",Toast.LENGTH_SHORT).show();
                    }
                });


        myPlantsbinding.plantsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), myPlantTasks.class);
                intent.putExtra("imageUrl", myplantImages.get(i));
                intent.putExtra("name", myplantName.get(i));
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        finish();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Plant Tasks Channel";
            String description = "Channel for plant-related tasks";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }





    private void scheduleNotifications() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        for (int i = 0; i < myplantWater.size(); i++) {
            Long item = myplantWater.get(i);

            Long notificationIntervalMillis = item * 60 * 1000; // Convert days to milliseconds

            // Generate a unique request code based on the position in the loop
            int requestCode = i;

            // Create an Intent to launch the WateringReminderService.
            Intent reminderIntent = new Intent(this, NotificationBroadcastReceiver.class);
            reminderIntent.putExtra("plantName", myplantName.get(i));

            // Create a unique PendingIntent for the alarm
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Schedule the alarm
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    notificationIntervalMillis,
                    pendingIntent
            );
        }



    }
}
