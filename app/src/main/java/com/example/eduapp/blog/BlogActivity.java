package com.example.eduapp.blog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.example.eduapp.HomeActivity;
import com.example.eduapp.Learn.LearnActivity;
import com.example.eduapp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity implements OnBlogClickListener {

    private RecyclerView recyclerView;

    private  List<BlogClass> blogClassList = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setActionBar(toolbar);

        ImageButton imageButton = (ImageButton) findViewById(R.id.back_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlogActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        final BlogAdapter adapter = new BlogAdapter(blogClassList,this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("Blog")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Hey", "Listen failed.", e);
                            return;
                        }

                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            String title = null, link = null, image = null;
                            if (doc.get("image") != null) {
                                image = (String) doc.get("image");
                            }
                            if(doc.get("title") != null) {
                                title = (String) doc.get("title");
                            }
                            if (doc.get("link") != null) {
                                link = (String) doc.get("link");
                            }
                            blogClassList.add(new BlogClass(image, title, link));
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("Hey", "Current cites in CA: " + cities);
                    }
                });

    }

    @Override
    public void onClick(int position) {
        String link = blogClassList.get(position).getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }
}