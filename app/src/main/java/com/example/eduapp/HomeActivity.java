package com.example.eduapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.eduapp.R;
import com.example.eduapp.posts.CreateActivity;
import com.example.eduapp.posts.adapters.MyPagerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;

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
                Intent i = new Intent(HomeActivity.this, CreateActivity.class);
                startActivity(i);
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
                        //TODO intent here
                        break;
                    case R.id.nav_tasks:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO intent here
                        break;
                    case R.id.nav_quiz:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO intent here
                        break;
                    default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
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