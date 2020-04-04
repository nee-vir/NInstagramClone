package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText lEmail,lPassword,sUsername,sPassword;
    private Button toSignUpButton,loginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        lEmail = findViewById(R.id.lEmail);
        lPassword = findViewById(R.id.lPassword);
        loginButton = findViewById(R.id.loginBtn);
        toSignUpButton=findViewById(R.id.toSignUpActivity);
        progressBar=findViewById(R.id.progressBar);
        loginButton.setOnClickListener(this);
        toSignUpButton.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            Intent intent=new Intent(LogInActivity.this,WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        switch (v.getId()){
            case R.id.loginBtn:
                ParseUser.logInInBackground(lEmail.getText().toString(), lPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            progressBar.setVisibility(View.GONE);
                            Intent intent=new Intent(LogInActivity.this,WelcomeActivity.class);
                            startActivity(intent);
                            FancyToast.makeText(LogInActivity.this,"Log In Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                            finish();
                        } else{
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(LogInActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                    }
                });
                break;
            case R.id.toSignUpActivity:
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(this,SignUpActivity.class));
                break;
        }
    }

    public void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
