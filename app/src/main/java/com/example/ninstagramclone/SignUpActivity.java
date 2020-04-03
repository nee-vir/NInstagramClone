package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText sUsername,sPassword;
    private Button signUpButton,buttonToLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sUsername=findViewById(R.id.sUsername);
        sPassword=findViewById(R.id.sPassword);
        signUpButton=findViewById(R.id.signUpBtn);
        buttonToLoginActivity=findViewById(R.id.toLoginActivity);
        signUpButton.setOnClickListener(this);
        buttonToLoginActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View tappedView) {

        switch (tappedView.getId()){
            case R.id.signUpBtn:
                ParseUser user=new ParseUser();
                user.setUsername(sUsername.getText().toString());
                user.setPassword(sPassword.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SignUpActivity.this,"Sign Up Success",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                            Intent intent=new Intent(SignUpActivity.this,LogInActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            FancyToast.makeText(SignUpActivity.this,e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }
                    }
                });
                break;
            case R.id.toLoginActivity:
                Intent intent=new Intent(this,LogInActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
