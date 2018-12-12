package com.uofk.ahmed_obied.mymemo;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class MemoPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Memo> mMemoList;

    private static final String EXTRA_ID = "com.uofk.ahmed_obied.mymemo.memoID";

    public static Intent newIntent(Context context, UUID MemoID){
        Intent intent = new Intent(context,MemoPagerActivity.class);
        intent.putExtra(EXTRA_ID,MemoID);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_pager);

        UUID Id = (UUID) getIntent().getSerializableExtra(EXTRA_ID);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mMemoList = MemoManager.get(this).getMemos();

        mViewPager = findViewById(R.id.memo_pager_view);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Memo memo = mMemoList.get(position);
                return MemoFragment.newFragment(memo.getId());
            }

            @Override
            public int getCount() {
                return mMemoList.size();
            }
        });


        for (int i=0; i<mMemoList.size(); i++){
            if (mMemoList.get(i).getId().equals(Id)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
