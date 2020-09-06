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

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    public AnswerAdapter(ArrayList<HashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }

    ArrayList<HashMap<String,Object>> dataList;

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_card,parent,false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        ImageView i = holder.imageView;
        Glide.with(i.getContext()).load(dataList.get(position).get("imageurl").toString()).into(i);
        holder.desc.setText(dataList.get(position).get("desc").toString());
        holder.author.setText(dataList.get(position).get("name").toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView desc,author;
        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.answer_image);
            desc = itemView.findViewById(R.id.answer_desc);
            author = itemView.findViewById(R.id.answer_author);

        }
    }



}
