package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> plantName;
    ArrayList<String> imageUrls;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<String> plantName, ArrayList<String> imageUrls) {
        this.context = context;
        this.plantName = plantName;
        this.imageUrls = imageUrls;


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

        if(inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null){

            view = inflater.inflate(R.layout.griditem,null);
        }

        ImageView imageView = view.findViewById(R.id.grid_image);
        //StorageReference storageReference = storage.getReference().child(imageUrls[i]);
        Glide.with(context).load(imageUrls.get(i)).into(imageView);

        TextView textView = view.findViewById(R.id.item_name);
        textView.setText(plantName.get(i));

        return view;
    }
}
