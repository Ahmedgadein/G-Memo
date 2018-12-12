package com.uofk.ahmed_obied.mymemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.MemoBaseHelper;
import database.MemoSchema.Table;
import database.MemoWrapper;

import static database.MemoSchema.Table.*;

public class MemoManager {
    private SQLiteDatabase mDatabase;
    private static MemoManager sMemoManager;

    public static MemoManager get(Context context) {
        if (sMemoManager == null)
            sMemoManager = new MemoManager(context);

        return sMemoManager;
    }

    private MemoManager(Context context) {
        mDatabase = new MemoBaseHelper(context).getWritableDatabase();
    }

    public List<Memo> getMemos() {
        List<Memo> memoList = new ArrayList<>();

        MemoWrapper memoWrapper = queryMemos(null,null);

        try{
            memoWrapper.moveToFirst();
            while (!memoWrapper.isAfterLast()){
                memoList.add(memoWrapper.getMemo());
                memoWrapper.moveToNext();
            }
        }finally {
            memoWrapper.close();
        }

        return memoList;
    }

    public Memo getMemo(UUID memoID) {
        String[] uuidstring = new String[] {memoID.toString()};
        MemoWrapper memoWrapper = queryMemos(Cols.UUID + " = ?", uuidstring);

        try {
            if(memoWrapper.getCount() == 0)
                return null;

            memoWrapper.moveToFirst();
            return memoWrapper.getMemo();
        }finally {
            memoWrapper.close();
        }
    }

    public void addMemo(Memo memo) {
        ContentValues contentValues = getContentValues(memo);
        mDatabase.insert(Table.NAME,null,contentValues);
    }


    public void updateMemo(Memo memo){
        ContentValues contentValues = getContentValues(memo);
        String[] uuidstring = new String[] {memo.getId().toString()};

        mDatabase.update(Table.NAME,contentValues,Cols.UUID + " = ?", uuidstring);
    }

    public void deleteMemo(Memo memo){
        String[] id = new String[] {memo.getId().toString()};
        mDatabase.delete(Table.NAME,Cols.UUID + " = ?", id);
    }

    private MemoWrapper queryMemos(String selection, String[] selectionArgs){
        Cursor cursor = mDatabase.query(
                Table.NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return new MemoWrapper(cursor);
    }

    private ContentValues getContentValues(Memo memo){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Cols.UUID, memo.getId().toString());
        contentValues.put(Cols.TEXT, memo.getText());
        contentValues.put(Cols.TITLE, memo.getTitle());
        contentValues.put(Cols.DATE, memo.getDate().getTime());

        return contentValues;
    }
}
