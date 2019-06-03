package com.example.mynotes.mvc.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.mynotes.mvc.Bean.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteModel {
    private List<Note> list=new ArrayList();
    private SQLiteDatabase db;
    private NoteDataBaseHelper dbHelper;

    public  NoteModel(NoteDataBaseHelper dbHelper)
    {
        //创建数据库和note_table表格
        this.dbHelper=dbHelper;
        db= dbHelper.getWritableDatabase();
    }

    public boolean addNote(String title, String content, long time)//添加note
    {
        try {
            dbHelper.getWritableDatabase();
            ContentValues cValue = new ContentValues();
            cValue.put("title", title);
            cValue.put("time", time);
            cValue.put("content", content);
            db.insert("NoteTable", null, cValue);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteNote(String title,long time)//删除note
    {
        try {
            dbHelper.getWritableDatabase();
            String sql="delete from NoteTable where title=? and time=?";
            db.execSQL(sql,new Object[]{title,time});
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Note> showNotes()//将数据库的信息装入实体类加入列表
    {
        String title, content;
        long time;
        int i;
        dbHelper.getReadableDatabase();
        Cursor cursor=db.query("NoteTable",null,null,null,null,null,null);
        i=cursor.getCount();
        if(i==0)
        {
            return list;
        }
        while(i>0)
        {
            cursor.moveToPosition(i-1);
            title=cursor.getString(0);
            time=cursor.getLong(1);
            content=cursor.getString(2);
            list.add(new Note(title,time,content));
            i--;
        }
        cursor.close();
        return list;
    }
}
