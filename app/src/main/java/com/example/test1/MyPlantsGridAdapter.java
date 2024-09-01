package com.example.test1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;

public class MyPlantsGridAdapter extends BaseAdapter {



    Context context;
    ArrayList<String> plantName;
    ArrayList<String> imageUrls;
    ArrayList<String> plantID;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    LayoutInflater inflater;

    public MyPlantsGridAdapter(Context context, ArrayList<String> plantName, ArrayList<String> imageUrls, ArrayList<String> plantID) {
        this.context = context;
        this.plantName = plantName;
        this.imageUrls = imageUrls;
        this.plantID = plantID;


    }

    @Override
    public int getCount() {
        return plantName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            view = inflater.inflate(R.layout.my_plants_griditem, null);
        }

        ImageView imageView = view.findViewById(R.id.myplant_grid_image);
        //StorageReference storageReference = storage.getReference().child(imageUrls[i]);
        Glide.with(context).load(imageUrls.get(i)).into(imageView);

        TextView textView = view.findViewById(R.id.myplant_item_name);
        textView.setText(plantName.get(i));

        ImageView moreOptions = view.findViewById(R.id.dots);
        moreOptions.setOnClickListener(v -> showPopupMenu(v, i));

        return view;
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_delete) {
                // Get the document ID to be deleted
                String documentId = plantID.get(position);

                // Delete the document from Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference userPlantsCollection = db.collection("users").document("userid").collection("user plants");
                userPlantsCollection.document(documentId).delete()
                        .addOnSuccessListener(aVoid -> {
                            // Document deleted successfully, now remove the item from the adapter
                            plantName.remove(position);
                            imageUrls.remove(position);
                            plantID.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Plant deleted successfully", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle deletion failure
                            Toast.makeText(context, "Failed to delete plant: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

}
