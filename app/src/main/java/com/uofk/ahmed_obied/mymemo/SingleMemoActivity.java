package com.uofk.ahmed_obied.mymemo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleMemoActivity extends AppCompatActivity{

    protected abstract Fragment getFragment();

    @LayoutRes
    protected int getResId(){
        return R.layout.activity_fragment;
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getResId());


            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

            if(fragment == null){
                fragment = getFragment();
                fragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit();
            }
        }
    }

