package com.example.ninstagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class PostActivity extends AppCompatActivity {
    private ProgressBar progressBarL;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        linearLayout=findViewById(R.id.linearLayout_p);
        progressBarL=findViewById(R.id.postProgressBar);
        Intent intent=getIntent();
        FancyToast.makeText(this,intent.getStringExtra("userName"),FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        progressBarL.setVisibility(View.VISIBLE);
        String receivedUsername=intent.getStringExtra("userName");
        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUsername);
        parseQuery.orderByDescending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for(ParseObject parseObject:objects){
                        final TextView postDes=new TextView(PostActivity.this);
                        postDes.setText(parseObject.get("images_des")+"");
                        ParseFile parseFile=(ParseFile)parseObject.get("picture");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null&& data!=null){
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImage=new ImageView(PostActivity.this);
                                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
                                    layoutParams.setMargins(5,5,5,5);
                                    postImage.setLayoutParams(layoutParams);
                                    postImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImage.setImageBitmap(bitmap);
                                    LinearLayout.LayoutParams dlayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dlayoutParams.setMargins(5,5,5,5);
                                    postDes.setLayoutParams(dlayoutParams);
                                    postDes.setGravity(Gravity.CENTER);
                                    postDes.setTextColor(Color.BLUE);
                                    postDes.setBackgroundColor(Color.WHITE);
                                    postDes.setTextSize(30f);
                                    linearLayout.addView(postImage);
                                    linearLayout.addView(postDes);
                                    progressBarL.setVisibility(View.GONE);
                                }
                            }
                        });

                    }
                }
                progressBarL.setVisibility(View.GONE);
            }

        });


    }
}
