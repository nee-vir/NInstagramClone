package com.example.ninstagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition){
            case 0:
                Profile profile=new Profile();
                return profile;
            case 1:
                return new Users();
            case 2:
                return new Share();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share";
            default:
                return null;

        }
    }
}
