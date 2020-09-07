package com.example.eduapp.posts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eduapp.R;
import com.example.eduapp.posts.adapters.AnswerAdapter;
import com.example.eduapp.posts.fragments.AnswerBottomSheet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    static public String TAG = "Post Activity";
    FirebaseFirestore ff;
    String postId;
    ArrayList<HashMap<String,Object>> dataList;
    AnswerAdapter answerAdapter;
    ImageView imageView;
    TextView title,description,author;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postId = getIntent().getStringExtra("postid");
        ff = FirebaseFirestore.getInstance();

        answerAdapter = new AnswerAdapter(dataList);
        imageView = findViewById(R.id.selected_post_image);
        title = findViewById(R.id.selected_post_title);
        description = findViewById(R.id.selected_post_desc);
        author = findViewById(R.id.selected_post_author);
        recyclerView = findViewById(R.id.selected_post_recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        answerAdapter = new AnswerAdapter(dataList);
        recyclerView.setAdapter(answerAdapter);

        loadData();
        loadAnswers();
    }

    private void loadAnswers() {

        ff.collection("Posts").document(postId).collection("answers").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            QueryDocumentSnapshot doc = dc.getDocument();
                            HashMap<String,Object> map = new HashMap<>();
                            if (doc.get("description") != null) {
                                map.put("desc",doc.get("description"));
                            }
                            if (doc.get("imageurl") != null) {
                                map.put("imageurl",doc.get("imageurl"));
                            }
                            if (doc.get("name") != null) {
                                map.put("name",doc.get("name"));
                            }
                            dataList.add(map);
                            answerAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
//                            Log.d(TAG, "Modified city: " + dc.getDocument().getData());
//                            break;
                        case REMOVED:
//                            Log.d(TAG, "Removed city: " + dc.getDocument().getData());
//                            break;
                    }
                }
            }
        });
    }

    private void loadData() {
        ff.collection("Posts").document(postId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        DocumentSnapshot doc = task.getResult();
                        if (doc.get("title") != null) {
                            title.setText((String)doc.get("title"));
                        }
                        if (doc.get("description") != null) {
                            description.setText((String)doc.get("description"));
                        }
                        if (doc.get("imageurl") != null) {
                            Glide.with(imageView.getContext()).load((String)doc.get("imageurl")).into(imageView);
                        }
                        if (doc.get("name") != null) {
                            author.setText((String)doc.get("name"));
                        }
                    }
                }
            }
        });
    }

    public void OnClick(View view){
        AnswerBottomSheet answerBottomSheet = new AnswerBottomSheet(postId);
        answerBottomSheet.show(getSupportFragmentManager(),answerBottomSheet.getTag());
    }


}