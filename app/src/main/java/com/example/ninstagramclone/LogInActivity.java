package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText lUsername,lPassword,sUsername,sPassword;
    private Button toSignUpButton,loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lUsername = findViewById(R.id.lUsername);
        lPassword = findViewById(R.id.lPassword);
        loginButton = findViewById(R.id.loginBtn);
        toSignUpButton=findViewById(R.id.toSignUpActivity);
        loginButton.setOnClickListener(this);
        toSignUpButton.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                ParseUser.logInInBackground(lUsername.getText().toString(), lPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            Intent intent=new Intent(LogInActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                            FancyToast.makeText(LogInActivity.this,"Log In Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                            finish();
                        } else{
                            FancyToast.makeText(LogInActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                    }
                });
                break;
            case R.id.toSignUpActivity:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
        }
    }
}
