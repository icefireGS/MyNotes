package com.example.mynotes.mvc.Bean;

public class Clock {
    private String title;//闹钟标题
    private int m_switch;//闹钟开关
    private MyDate mydate;//闹钟时间
    private long time;//创建时间

    public Clock(String title,int sw,MyDate mydate)
    {
        this.title=title;
        this.m_switch=sw;
        this.mydate=mydate;
        this.time=System.currentTimeMillis();
    }

    public Clock()
    {
        this.title=null;
        this.m_switch=0;
        mydate=new MyDate();
        this.time=System.currentTimeMillis();
    }

    public String GetTitle()//获取闹钟名字
    {
        return this.title;
    }

    public int GetSwitch()//获取闹钟类容
    {
        return this.m_switch;
    }

    public MyDate GetDate()//获取闹钟时间
    {
        return  this.mydate;
    }

    public long GetTime()//获取创建时间
    {
        return this.time;
    }

    public void SetTitle(String title)//设置闹钟名字
    {
        this.title=title;
    }

    public void SetSwitch(int sw)//设置闹钟内容
    {
        this.m_switch=sw;
    }

    public void SetDate(MyDate date)//设置闹钟时间
    {
        this.mydate=date;
    }
}
