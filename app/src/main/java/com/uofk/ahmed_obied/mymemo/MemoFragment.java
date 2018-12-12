package com.uofk.ahmed_obied.mymemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class MemoFragment extends Fragment {
    private Memo mMemo;
    private EditText mTitle;
    private EditText mText;
    private MemoManager mMemoManager;

    private static final String ARG_MEMO_ID = "memoID";

    public static MemoFragment newFragment(UUID mId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MEMO_ID,mId);

        MemoFragment memoFragment = new MemoFragment();
        memoFragment.setArguments(bundle);

        return memoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID Id = (UUID) getArguments().getSerializable(ARG_MEMO_ID);
        mMemoManager = MemoManager.get(getContext());
        mMemo = mMemoManager.getMemo(Id);
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mMemo.getTitle() == null && mMemo.getText() == null)
            mMemoManager.deleteMemo(mMemo);
        mMemoManager.updateMemo(mMemo);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo,container,false);

        mTitle = view.findViewById(R.id.memo_title_edittext);
        mTitle.setText(mMemo.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left blank intentionally
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMemo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Left Blank intentionally
            }
        });

        mText = view.findViewById(R.id.memo_text_edittext);
        mText.setText(mMemo.getText());
        mText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Left Blank intentionally
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMemo.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Left Blank intentionally
            }
        });

        return view;
    }
}
