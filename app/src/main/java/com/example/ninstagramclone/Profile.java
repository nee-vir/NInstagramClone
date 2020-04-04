package com.example.ninstagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment implements View.OnClickListener{

    private EditText textName,textBio,textProfession,textHobbies,textFavSport;
    private Button updateButton;
    ParseUser parseUser;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        textName=view.findViewById(R.id.name_et);
        textBio=view.findViewById(R.id.bio_et);
        textProfession=view.findViewById(R.id.profession_et);
        textHobbies=view.findViewById(R.id.hobbies_et);
        textFavSport=view.findViewById(R.id.fav_sport_et);
        updateButton=view.findViewById(R.id.updateInfoBtn);
        parseUser=ParseUser.getCurrentUser();
        updateButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateInfoBtn:
                parseUser.put("profileName",textName.getText().toString());
                parseUser.put("bio",textBio.getText().toString());
                parseUser.put("profession",textProfession.getText().toString());
                parseUser.put("hobbies",textHobbies.getText().toString());
                parseUser.put("favouriteSport",textFavSport.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),"Updated Successfully",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
                        } else{
                            FancyToast.makeText(getContext(),"Failed to Update",FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show();
                        }
                    }
                });

                break;
        }
    }
}
