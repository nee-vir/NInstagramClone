package com.example.ninstagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class Users extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public Users() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_users, container, false);
        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseUser user:objects){
                            arrayList.add(user.getUsername());
                        }
                        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }
        });
        listView.setOnItemClickListener(Users.this);
        listView.setOnItemLongClickListener(Users.this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getContext(),PostActivity.class);
        intent.putExtra("userName",arrayList.get(position));
        startActivity(intent);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final PrettyDialog prettyDialog=new PrettyDialog(getContext());
        prettyDialog.setIcon(R.drawable.ic_person_black_24dp)
                .setTitle(arrayList.get(position).toString())
                .setMessage("Hi how are you?")
                .addButton(
                        "OK",					// button text
                        R.color.pdlg_color_white,		// button text color
                        R.color.pdlg_color_green,		// button background color
                        new PrettyDialogCallback() {		// button OnClick listener
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                );
        prettyDialog.show();
        return true;
    }
}
