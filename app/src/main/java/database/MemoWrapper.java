package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.uofk.ahmed_obied.mymemo.Memo;

import java.util.Date;
import java.util.UUID;

import static database.MemoSchema.Table.*;

public class MemoWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MemoWrapper(Cursor cursor) {
        super(cursor);
    }

    public Memo getMemo(){
        String uuid = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        String text = getString(getColumnIndex(Cols.TEXT));
        long date = getLong(getColumnIndex(Cols.DATE));

        Memo memo = new Memo(UUID.fromString(uuid));
        memo.setText(text);
        memo.setTitle(title);
        memo.setDate(new Date(date));

        return memo;
    }
}
