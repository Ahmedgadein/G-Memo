package com.uofk.ahmed_obied.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MemoListFragment extends Fragment {
    private boolean mSubtitleVisible;
    private RecyclerView MemoRecyclerView;
    private MemoManager mMemoManager;
    private MemoAdapter mMemoAdapter;
    private FloatingActionButton mFloatingMemoButton;

    private static final String SAVED_SUBTITLE = "subtitle";

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE, mSubtitleVisible);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list,container,false);

        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE);
        }

        mMemoManager = MemoManager.get(getContext());

        MemoRecyclerView = view.findViewById(R.id.memo_recyclerview);
        MemoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFloatingMemoButton = view.findViewById(R.id.memo_add_fab);
        mFloatingMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = new Memo();
                mMemoManager.addMemo(memo);
                Intent intent = MemoPagerActivity.newIntent(getContext(),memo.getId());
                startActivity(intent);

            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){

        if(mMemoAdapter == null){
            List<Memo> memoList = MemoManager.get(getContext()).getMemos();
            mMemoAdapter = new MemoAdapter(memoList);
            MemoRecyclerView.setAdapter(mMemoAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(mMemoAdapter));
            itemTouchHelper.attachToRecyclerView(MemoRecyclerView);
        }else {
            List<Memo> memoList = MemoManager.get(getContext()).getMemos();
            mMemoAdapter.setMemoList(memoList);
            mMemoAdapter.notifyDataSetChanged();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(mMemoAdapter));
            itemTouchHelper.attachToRecyclerView(MemoRecyclerView);
        }

        updateSubtitle();
    }

    private class MemoHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private Memo mMemo;
        private TextView mTitle;
        private TextView mText;
        private TextView mDate;

        public MemoHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.memo_title);
            mText = itemView.findViewById(R.id.memo_text);
            mDate = itemView.findViewById(R.id.memo_date);
            itemView.setOnClickListener(this);
        }

        public void bind(Memo memo){
            mMemo = memo;
            mTitle.setText(mMemo.getTitle());
            mText.setText(mMemo.getText());
            mDate.setText(getFormatedDate(mMemo.getDate()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = MemoPagerActivity.newIntent(getContext(),mMemo.getId());
            startActivity(intent);
        }
    }

    private class MemoAdapter extends RecyclerView.Adapter<MemoListFragment.MemoHolder>{
        private List<Memo> mMemoList;

        public MemoAdapter(List<Memo> memoList) {
            mMemoList = memoList;
        }

        public void setMemoList(List<Memo> memoList){
            mMemoList = memoList;
        }

        @Override
        public MemoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.list_item_memo,parent,false);

            return new MemoHolder(view);
        }

        @Override
        public void onBindViewHolder(MemoHolder holder, int position) {
            Memo memo = mMemoList.get(position);
            holder.bind(memo);
        }

        @Override
        public int getItemCount() {
            return mMemoList.size();
        }

        public void deleteItem(int position){
            mMemoManager.deleteMemo(mMemoList.get(position));
            mMemoList.remove(position);
            notifyItemRemoved(position);
            updateSubtitle();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_memo_list,menu);

        MenuItem menuItem = menu.findItem(R.id.show_subtitle);

        if (mSubtitleVisible){
            menuItem.setTitle(R.string.hide_subtitle_label);
        }else{
            menuItem.setTitle(R.string.show_subtitle_label);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){
        int MemoNumber = MemoManager.get(getContext()).getMemos().size();
        String subtitle;

        subtitle = (MemoNumber == 1) ? getString(R.string.subtitle_single_memo) : getString(R.string.subtitle_memos_format,MemoNumber);

        if(!mSubtitleVisible)
            subtitle = null;

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public static String getFormatedDate(Date date){

        String dateFormated = DateFormat.format("EEE, dd/MM HH:mm",date).toString();
        return dateFormated;
    }

    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback{
        private MemoAdapter mMemoAdapter;

        public SwipeToDeleteCallback(MemoAdapter memoAdapter) {
            super(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
            mMemoAdapter = memoAdapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mMemoAdapter.deleteItem(position);
        }
    }
}
