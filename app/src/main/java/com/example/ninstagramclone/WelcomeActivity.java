package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        toolbar=findViewById(R.id.myToolbar);
        toolbar.setTitle("N Instagram");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        tabAdapter=new TabAdapter(getSupportFragmentManager(),1);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager,false);


    }
}
