package com.example.eduapp.posts.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduapp.R;
import com.example.eduapp.posts.adapters.MyRecyclerAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AllPostsFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> dataList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore ff;
    private static String TAG = "AllPostsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_posts, container, false);

        dataList = new ArrayList<>();
        ff = FirebaseFirestore.getInstance();
        startLoading();

        recyclerView = view.findViewById(R.id.all_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyRecyclerAdapter(dataList);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private void startLoading() {
        ff.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            if (doc.get("title") != null) {
                                map.put("title",doc.get("title"));
                            }
                            if (doc.get("class") != null) {
                                map.put("class",doc.get("class"));
                            }
                            if (doc.get("date") != null) {
                                map.put("date",doc.get("date"));
                            }
                            if (doc.get("imageurl") != null) {
                                map.put("imageurl",doc.get("imageurl"));
                            }
                            dataList.add(map);
                            mAdapter.notifyDataSetChanged();
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
}