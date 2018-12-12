package com.uofk.ahmed_obied.mymemo;

import java.util.Date;
import java.util.UUID;

public class Memo {
    private String mText;
    private UUID Id;
    private Date mDate;
    private String mTitle;

    public Memo(UUID Id){

        this.Id = Id;
        mDate = new Date();
    }

    public Memo(){
        this(UUID.randomUUID());
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
