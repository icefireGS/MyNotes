package com.example.mynotes.mvc.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynotes.mvc.Bean.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteModel {
    private List<Note> list=new ArrayList();
    private SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("/data/node.db",null);

    public  NoteModel()
    {
        //创建node_table表格
        String node_table="create table NodeTable(title String primary key autoincrement, time long, content String)";
        db.execSQL(node_table);
    }

    public void addNote(String title, String content, long time)//添加note
    {
        ContentValues cValue= new ContentValues();
        cValue.put("title",title);
        cValue.put("time",time);
        cValue.put("content",content);
        db.insert("node_table",null,cValue);
    }

    public void deleteNote(String title)//删除note
    {
        db.delete("node_table","title=?",new String[]{title});
    }

    public List<Note> showNotes()//将数据库的信息装入实体类加入列表
    {
        String title, content;
        long time;
        Cursor cursor=db.query("node_table",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.move(i);
                title=cursor.getString(0);
                time=cursor.getLong(1);
                content=cursor.getString(1);
                list.add(new Note(title,time,content));
            }
        }
        return list;
    }
}
