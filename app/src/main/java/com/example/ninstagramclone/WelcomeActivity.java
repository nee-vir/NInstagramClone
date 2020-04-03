package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeText=findViewById(R.id.textWelcome);
        logOutButton=findViewById(R.id.logOutBtn);
        welcomeText.setText("Welcome! " + ParseUser.getCurrentUser().getUsername());
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(WelcomeActivity.this,"Log Out Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                            Intent intent=new Intent(WelcomeActivity.this,SignUpLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            FancyToast.makeText(WelcomeActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }

                    }
                });
            }
        });
    }
}
