package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText sEmail,sUsername,sPassword;
    private Button signUpButton,buttonToLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        sEmail=findViewById(R.id.sEmail);
        sUsername=findViewById(R.id.sUsername);
        sPassword=findViewById(R.id.sPassword);
        sPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(signUpButton);
                }
                return false;
            }
        });
        signUpButton=findViewById(R.id.signUpBtn);
        buttonToLoginActivity=findViewById(R.id.toLoginActivity);
        signUpButton.setOnClickListener(this);
        buttonToLoginActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View tappedView) {

        switch (tappedView.getId()){

            case R.id.signUpBtn:
                if(sEmail.getText().toString().equals("")||sUsername.getText().toString().equals("")||sPassword.getText().toString().equals("")){
                    FancyToast.makeText(this,"Email, Username and Password fields are required!",
                            FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();

                } else {
                    ParseUser user = new ParseUser();
                    user.setEmail(sEmail.getText().toString());
                    user.setUsername(sUsername.getText().toString());
                    user.setPassword(sPassword.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUpActivity.this, "Sign Up Success",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FancyToast.makeText(SignUpActivity.this, e.getMessage(),
                                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }
                break;
            case R.id.toLoginActivity:
                Intent intent=new Intent(this,LogInActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
    public void sRootLayoutTapped(View view){ //To hide the keyboard when the root layout is tapped
        try{
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
