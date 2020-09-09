package com.example.eduapp.blog;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import static android.content.Intent.ACTION_VIEW;

public class BlogClass {

        private String imageResource;
        private String title;
        private String link;

        public BlogClass(String imageResource, String title, String link) {
            this.imageResource = imageResource;
            this.title = title;
            this.link = link;
        }

        public String getImageResource() {
            return imageResource;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }
}

