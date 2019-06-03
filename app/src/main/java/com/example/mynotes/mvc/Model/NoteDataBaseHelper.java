package com.example.mynotes.mvc.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String note_table="create table NoteTable(title String,"+"time long,"+"content String)";

    public NoteDataBaseHelper(Context context,String name,int version)
    {
        super(context,name,null,version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(note_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int version){}

}
