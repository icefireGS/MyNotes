package com.example.mynotes.mvc.Model;

import java.util.Date;

public class Note {
    private String title;//笔记标题
    private String content;//笔记内容
    private long time;//保存日期

    public Note(String titlte, long time, String content)
    {
        this.title=title;
        this.time=time;
        this.content=content;
    }

    public String getTitle()
    {
        return this.titlte;
    }

    public void setTitle(String title)
    {
        this.titlte=title;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(String content)
    {
        this.content=content;
    }

    public long getTime()
    {
        return this.time;
    }

    public void setTime(long time)
    {
        this.time=time;
    }

}
