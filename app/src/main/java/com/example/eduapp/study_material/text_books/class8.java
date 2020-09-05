package com.example.eduapp.study_material.text_books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eduapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class class8 extends AppCompatActivity {

    ListView myListView;
    DatabaseReference mRef;

    List<getPDF> pdfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        myListView = (ListView) findViewById(R.id.myListView);
        pdfFiles = new ArrayList<>();

        viewAllFiles();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                getPDF pdf = pdfFiles.get(i);


                Intent intent = new Intent();
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);
            }
        });


    }

    private void viewAllFiles() {
        mRef = FirebaseDatabase.getInstance().getReference("eighth");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    getPDF pdfFile = postSnapShot.getValue(getPDF.class);
                    pdfFiles.add(pdfFile);
                }

                String[] pdfNames = new String[pdfFiles.size()];

                for(int i = 0; i < pdfNames.length; ++i) {
                    pdfNames[i] = pdfFiles.get(i).getName();
                }

                ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pdfNames) {
                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView myText = (TextView) view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);

                        return view;
                    }
                };
                myListView.setAdapter(myArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}