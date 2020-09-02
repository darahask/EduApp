package com.example.eduapp.posts.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eduapp.posts.fragments.AllPostsFragment;
import com.example.eduapp.posts.fragments.ClassPostsFragment;
import com.example.eduapp.posts.fragments.YourPostsFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"All posts","Class Posts","My Posts"};

    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new AllPostsFragment();
        }else if(position == 1){
            return new ClassPostsFragment();
        }else{
            return new YourPostsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }
}
