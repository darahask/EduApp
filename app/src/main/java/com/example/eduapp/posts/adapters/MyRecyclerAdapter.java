package com.example.eduapp.posts.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private ArrayList<HashMap<String,Object>> dataList;

    public MyRecyclerAdapter(ArrayList<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.MyViewHolder holder, int position) {
        ImageView i = holder.imageView;
        Glide.with(i.getContext()).load(dataList.get(position).get("imageurl").toString()).into(i);
        holder.title.setText(dataList.get(position).get("title").toString());
        holder.class_name.setText(dataList.get(position).get("class").toString());
        holder.date.setText("05-05-2020");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView title,class_name,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            title = itemView.findViewById(R.id.card_title);
            class_name = itemView.findViewById(R.id.card_class);
            date = itemView.findViewById(R.id.card_date);
        }
    }
}
