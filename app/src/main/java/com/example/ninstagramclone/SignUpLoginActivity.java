package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText lUsername,lPassword,sUsername,sPassword;
    private Button signUpButton,loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lUsername=findViewById(R.id.lUsername);
        lPassword=findViewById(R.id.lPassword);
        sUsername=findViewById(R.id.sUsername);
        sPassword=findViewById(R.id.sPassword);
        signUpButton=findViewById(R.id.signUpBtn);
        loginButton=findViewById(R.id.loginBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user=new ParseUser();
                user.setUsername(sUsername.getText().toString());
                user.setPassword(sPassword.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignUpLoginActivity.this,"Sign Up Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        } else{
                            FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(lUsername.getText().toString(), lPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            FancyToast.makeText(SignUpLoginActivity.this,"Log In Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                        } else{
                            FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });
    }
}
