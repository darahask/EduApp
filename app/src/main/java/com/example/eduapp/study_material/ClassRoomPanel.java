package com.example.eduapp.study_material;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eduapp.R;
import com.example.eduapp.study_material.text_books.class10;
import com.example.eduapp.study_material.text_books.class6;
import com.example.eduapp.study_material.text_books.class7;
import com.example.eduapp.study_material.text_books.class8;
import com.example.eduapp.study_material.text_books.class9;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ClassRoomPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room_panel);

        ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList<String> Classes = new ArrayList<String>(asList("6 th standard", "7 th standard", "8 th standard", "9 th standard", "10 th standard"));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Classes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0 :
                        startActivity(new Intent(getApplicationContext(), class6.class));
                        break;
                    case 1 :
                        startActivity(new Intent(getApplicationContext(), class7.class));
                        break;
                    case 2 :
                        startActivity(new Intent(getApplicationContext(), class8.class));
                        break;
                    case 3 :
                        startActivity(new Intent(getApplicationContext(), class9.class));
                        break;
                    case 4 :
                        startActivity(new Intent(getApplicationContext(), class10.class));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "No internet or error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}