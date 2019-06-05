package com.example.mynotes.mvc.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynotes.mvc.Bean.Clock;
import com.example.mynotes.mvc.Bean.MyDate;

import java.util.ArrayList;
import java.util.List;

public class ClockModel {
    private List<Clock> list=new ArrayList();
    private SQLiteDatabase db;
    private ClockDataBaseHelper dbHelper;

    public  ClockModel(ClockDataBaseHelper dbHelper)
    {
        //ClockTable表格
        this.dbHelper=dbHelper;
        db= dbHelper.getWritableDatabase();
    }

    public boolean addClock(String title, int t_sw, MyDate t_date)//添加clock
    {
        try {
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            Clock t_clock=new Clock(title,t_sw,t_date);
            ContentValues cValue = new ContentValues();
            cValue.put("title", title);
            cValue.put("switch", t_sw);
            cValue.put("time",t_clock.GetTime());
            cValue.put("year", t_date.GetYear());
            cValue.put("month", t_date.GetMonth());
            cValue.put("day", t_date.GetDay());
            cValue.put("minute", t_date.GetMinute());
            db.insert("ClockTable", null, cValue);
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteClock(long time)//删除clock
    {
        try {
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            String sql="delete from NoteTable where time=?";
            db.execSQL(sql,new Object[]{time});
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean UpdateNote(long time,Clock t_clock)//更新数据
    {
        try {
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            MyDate t_date=t_clock.GetDate();
            String sql="update ClockTable set title=? ,year=? , month=? , day=? , minute=? ,time=? where time=?";
            db.execSQL(sql,new Object[]{t_clock.GetTitle(),t_date.GetYear(),t_date.GetMonth(),t_date.GetDay(),t_date.GetMinute(),time,time});
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void UpdateSwitch(int i,long time)//更新开关
    {
        try {
            if(!db.isOpen())
            {
                dbHelper.getWritableDatabase();
            }
            db.execSQL("update ClockTable set switch=? where time=?",new Object[]{i,time});
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Clock> showClock()//将数据库的信息装入实体类加入列表
    {
        String title;
        MyDate t_date=new MyDate();
        long time;
        int i,b;
        if(!db.isOpen())
        {
            dbHelper.getReadableDatabase();
        }
        Cursor cursor=db.query("ClockTable",null,null,null,null,null,null);
        i=cursor.getCount();
        if(i==0)
        {
            cursor.close();
            return list;
        }
        while(i>0)
        {
            cursor.moveToPosition(i-1);
            title=cursor.getString(0);
            b=cursor.getInt(1);
            time=cursor.getLong(2);
            t_date.SetYear(cursor.getString(3));
            t_date.SetMonth(cursor.getString(4));
            t_date.SetDay(cursor.getString(5));
            t_date.SetMinute(cursor.getString(6));
            list.add(new Clock(title,b,t_date));
            i--;
        }
        cursor.close();
        return list;
    }
}
