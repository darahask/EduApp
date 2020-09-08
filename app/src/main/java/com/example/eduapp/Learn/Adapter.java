package com.example.eduapp.Learn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eduapp.R;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    private OnCardClickListener cardClick;

    private List<ModelClass> modelClassList;

    public Adapter(List<ModelClass> modelClassList, OnCardClickListener cardClick) {
        this.modelClassList = modelClassList;
        this.cardClick = cardClick;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new Viewholder(view,cardClick);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String resource = modelClassList.get(position).getImageResource();
        String title = modelClassList.get(position).getTitle();
        String link = modelClassList.get(position).getLink();

        Glide.with(viewholder.imageView.getContext()).load(Uri.parse(resource)).placeholder(R.drawable.idea).into(viewholder.imageView);

        viewholder.title.setText(title);

    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView title;
        private OnCardClickListener cardClick;

        public Viewholder(@NonNull View itemView, OnCardClickListener cardClick) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
            this.cardClick = cardClick;
        }

//        private void setData(String titleText) {
//            title.setText(titleText);
//        }

        @Override
        public void onClick(View view) {
            cardClick.onClick(getAdapterPosition());
        }
    }

}
