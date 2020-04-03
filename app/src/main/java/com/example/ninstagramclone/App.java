package com.example.ninstagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("cXIkkIlYfnvQpfsXVVIgMqOoqLUSTsKzWYainhNJ")
                // if defined
                .clientKey("eB0r7ys2ppmGvoAbpJc2INVBEJQwxgadlHhmS56V")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
