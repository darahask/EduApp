package com.example.eduapp.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduapp.blog.BlogAdapter;
import com.example.eduapp.blog.BlogClass;
import com.example.eduapp.blog.OnBlogClickListener;
import com.example.eduapp.R;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.Viewholder> {

        private OnBlogClickListener blogClick;

        private List<BlogClass> blogClassList;

        public BlogAdapter(List<BlogClass> blogClassList, OnBlogClickListener blogClick) {
            this.blogClassList = blogClassList;
            this.blogClick = blogClick;
        }

        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_blog,viewGroup,false);
            return new Viewholder(view,blogClick);
        }

        @Override
        public void onBindViewHolder(@NonNull BlogAdapter.Viewholder viewholder, int position) {
            String resource = blogClassList.get(position).getImageResource();
            String title = blogClassList.get(position).getTitle();
            String link = blogClassList.get(position).getLink();

            Glide.with(viewholder.imageView.getContext()).load(Uri.parse(resource)).placeholder(R.drawable.idea).into(viewholder.imageView);

            viewholder.title.setText(title);

        }

        @Override
        public int getItemCount() {
            return blogClassList.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public ImageView imageView;
            public TextView title;
            private OnBlogClickListener blogClick;

            public Viewholder(@NonNull View itemView, OnBlogClickListener blogClick) {
                super(itemView);

                imageView = itemView.findViewById(R.id.item_image);
                title = itemView.findViewById(R.id.title);
                itemView.setOnClickListener(this);
                this.blogClick = blogClick;
            }

            @Override
            public void onClick(View view) {
                blogClick.onClick(getAdapterPosition());
            }
        }

    }
