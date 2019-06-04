package com.example.mynotes.mvc.Bean;

public class Clock {
    private String name;//闹钟名字
    private String content;//闹钟内容
    private MyDate mydate;//闹钟时间

    public Clock(String name,String content,MyDate mydate)
    {
        this.name=name;
        this.content=content;
        this.mydate=mydate;
    }

    public Clock()
    {
        this.name=null;
        this.content=null;
        mydate=new MyDate();
    }

    public String GetName()//获取闹钟名字
    {
        return this.name;
    }

    public String GetContent()//获取闹钟类容
    {
        return this.content;
    }

    public MyDate GetDate()//获取闹钟时间
    {
        return  this.mydate;
    }

    public void SetName(String name)//设置闹钟名字
    {
        this.name=name;
    }

    public void SetContent(String content)//设置闹钟内容
    {
        this.content=content;
    }

    public void SetDate(MyDate date)//设置闹钟时间
    {
        this.mydate=date;
    }
}
