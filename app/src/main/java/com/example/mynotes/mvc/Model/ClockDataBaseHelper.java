package com.example.mynotes.mvc.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClockDataBaseHelper  extends SQLiteOpenHelper {
    private Context context;
    private static final String clock_table="create table ClockTable(title String,"+"switch int,"+"time long,"+"year String,"+"month String,"+"day String,"+"minute String)";

    public ClockDataBaseHelper(Context context,String name,int version)
    {
        super(context,name,null,version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(clock_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int version){}

}
