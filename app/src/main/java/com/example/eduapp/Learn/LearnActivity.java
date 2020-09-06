package com.example.eduapp.Learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.eduapp.R;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity implements OnCardClickListener {

    private RecyclerView recyclerView;

    private  List<ModelClass> modelClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        modelClassList.add(new ModelClass("https://miro.medium.com/max/875/1*OSO-o6jHDwg3m_7kWSlaFA.jpeg","Solve me","https://aplusclick.org/ThinkOutsideTheBox.htm"));
        modelClassList.add(new ModelClass("https://cdn.nabita.org/website-media/nabita.org/wp-content/uploads/2020/02/13173238/thinking-guy.jpg","Ever wondered",""));
        modelClassList.add(new ModelClass("https://cdn2.hubspot.net/hub/145335/hubfs/10-outside-the-box-inbound-marketing-ideas.jpg?length=980&name=10-outside-the-box-inbound-marketing-ideas.jpg","Try these","https://www.funwithpuzzles.com/2011/08/lateral-thinking.html"));
//        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background,"user four","https://www.youtube.com/watch?v=AOPMlIIg_38&list=RDNgSFun7F8dI&index=4"));
//        modelClassList.add(new ModelClass(R.drawable.ic_launcher_background,"user five","https://www.google.com"));

        Adapter adapter = new Adapter(modelClassList,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        if(position%2==0) {
            String link = modelClassList.get(position).getLink();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(intent);
        }
        switch(position) {
            case 1:
                Intent wonderIntent = new Intent(this, WonderActivity.class);
                startActivity(wonderIntent);
                break;
            case 3:
                Intent exampleIntent = new Intent(this, ExampleActivity.class);
                startActivity(exampleIntent);
                break;
        }
    }

}