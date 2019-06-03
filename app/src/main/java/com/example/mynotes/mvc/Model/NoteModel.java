package com.example.mynotes.mvc.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.mynotes.mvc.Bean.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
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
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            String sql="delete from NoteTable where title=? and time=?";
            db.execSQL(sql,new Object[]{title,time});
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean UpdateNote(String title,long time,Note note)//更新数据
    {
        try {
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            String sql="update NoteTable set title=? ,time=?,content=? where title=? and time=?";
            db.execSQL(sql,new Object[]{note.getTitle(),note.getTime(),note.getContent(),title,time});
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean IsExist(String title,long time)
    {
        if(!db.isOpen())
        {
            dbHelper.getReadableDatabase();
        }
        String t=Long.toString(time);
        Cursor cursor=db.rawQuery("select title from NoteTable where title=? and time=?",new String[]{title,t});
        if(cursor.getCount()==0)
        {
            return false;
        }
        else
            return true;
    }

    public Note QuerryNote(String title,long time)
    {
        if(!db.isOpen())
        {
            dbHelper.getReadableDatabase();
        }
        Note note=new Note();
        if(!IsExist(title,time))
        {
            note.setTitle("已删除！");
            return note;
        }
        String t=Long.toString(time);
        Cursor cursor=db.rawQuery("select title from NoteTable where title=? and time=?",new String[]{title,t});
        note.setTitle(cursor.getString(0));
        note.setTime(cursor.getLong(1));
        note.setContent(cursor.getString(2));
        return note;
    }

    public List<Note> showNotes()//将数据库的信息装入实体类加入列表
    {
        String title, content;
        long time;
        int i;
        if(!db.isOpen())
        {
            dbHelper.getReadableDatabase();
        }
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
