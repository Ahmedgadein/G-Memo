package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.MemoSchema.Table;
import database.MemoSchema.Table.Cols;

public class MemoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Memo.db";


    public MemoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Cols.UUID + ", " +
                Cols.TITLE + ", " +
                Cols.DATE + ", " +
                Cols.TEXT + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
