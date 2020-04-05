package com.example.ninstagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class Share extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE = 2000;
    private ImageView imageView;
    private EditText description;
    private Button shareButton;
    Bitmap receivedImageBitmap;
    private Uri selectedImage;

    public Share() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share, container, false);
        imageView=view.findViewById(R.id.image_one);
        description=view.findViewById(R.id.textDescription);
        shareButton=view.findViewById(R.id.btnShare);
        imageView.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_one:
                if(Build.VERSION.SDK_INT>23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else{
                    getChosenImage();
                }
                break;
            case R.id.btnShare:
                if(receivedImageBitmap!=null){
                    if(description.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Description cannot be left empty",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    } else{
                        final ProgressDialog dialog=new ProgressDialog(getContext());
                        dialog.setMessage("Loading...");
                        dialog.show();
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte [] bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile=new ParseFile("pic.png",bytes);
                        ParseObject parseObject=new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("images_des",description.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    FancyToast.makeText(getContext(),"Shared Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                } else {
                                    FancyToast.makeText(getContext(),"Something went wrong",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                } else{
                    FancyToast.makeText(getContext(),"No Image Selected",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
                break;
        }
    }

    private void getChosenImage() {

//        FancyToast.makeText(getContext(),"Now we can access the images",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //This part is not working
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            if(resultCode==Activity.RESULT_OK&&data!=null){
                try{
                    selectedImage=data.getData();
                    receivedImageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
//                    Glide.with(this)
//                            .load(selectedImage)
//                            .centerCrop()
//                            .into(imageView);
                    imageView.setImageBitmap(receivedImageBitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
    }
    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
