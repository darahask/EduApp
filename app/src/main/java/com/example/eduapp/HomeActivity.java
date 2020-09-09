package com.example.eduapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eduapp.Learn.LearnActivity;
import com.example.eduapp.blog.BlogActivity;
import com.example.eduapp.posts.adapters.MyPagerAdapter;
import com.example.eduapp.posts.fragments.PostBottomSheet;
import com.example.eduapp.study_material.ClassRoomPanel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager viewPager = findViewById(R.id.content_view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(myPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.content_tab);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ham);

        fab = findViewById(R.id.home_create_launcher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostBottomSheet postBottomSheet = new PostBottomSheet();
                postBottomSheet.show(getSupportFragmentManager(),postBottomSheet.getTag());
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_post:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_material:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), ClassRoomPanel.class));
                        break;
                    case R.id.nav_tasks:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), TaskActivity.class));
                        break;
                    case R.id.nav_quiz:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent learn = new Intent(getApplicationContext(),LearnActivity.class);
                        startActivity(learn);
                        break;
                    case R.id.nav_blog:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent blog = new Intent(getApplicationContext(), BlogActivity.class);
                        startActivity(blog);
                        break;
                    default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mailto:"));
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eduappse@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for EduApp");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                }
                return true;
            }
        });

        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.get("name") != null) {
                            TextView textView = findViewById(R.id.navheader_title);
                            textView.setText((String)doc.get("name") + " ");
                        }
                        if (doc.get("class") != null) {
                            TextView textView = findViewById(R.id.navheader_class);
                            textView.setText((String)doc.get("class") + " ");
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(R.id.log_out == item_id){
            FirebaseAuth.getInstance().signOut();
            finish();
        }else{
            if(android.R.id.home == item_id){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }
}