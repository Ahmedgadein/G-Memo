package com.uofk.ahmed_obied.mymemo;

import android.support.v4.app.Fragment;

public class MemoListActivity extends SingleMemoActivity {


    @Override
    protected Fragment getFragment() {
        return new MemoListFragment();
    }
}
