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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private ArrayList<HashMap<String,Object>> dataList;
    private OnPostClickListener onClickListener;

    public MyRecyclerAdapter(ArrayList<HashMap<String, Object>> dataList, OnPostClickListener onClickListener) {
        this.dataList = dataList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new MyViewHolder(v,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.MyViewHolder holder, int position) {
        ImageView i = holder.imageView;
        String url = dataList.get(position).get("imageurl").toString();
        if(!url.equalsIgnoreCase("")){
            Glide.with(i.getContext()).load(url).into(i);
        }
        holder.title.setText(dataList.get(position).get("title").toString());
        holder.class_name.setText(dataList.get(position).get("class").toString());
        String date = new SimpleDateFormat("dd/MM/yyyy").format(dataList.get(position).get("date"));
        holder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView title,class_name,date;
        public OnPostClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView, OnPostClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.card_image);
            title = itemView.findViewById(R.id.card_title);
            class_name = itemView.findViewById(R.id.card_class);
            date = itemView.findViewById(R.id.card_date);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onPostClick(getAdapterPosition());
        }
    }
}
