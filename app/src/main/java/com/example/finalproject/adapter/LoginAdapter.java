package com.example.finalproject.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.finalproject.fragment.LoginFragment;
import com.example.finalproject.fragment.SignupFragment;


public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTab;

    public LoginAdapter(FragmentManager fm, Context context, int totalTab){
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }

    @Override
    public int getCount() {
        return totalTab;
    }

    public Fragment getItem(int position){
        switch (position){
            case 1:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 2:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }
}
