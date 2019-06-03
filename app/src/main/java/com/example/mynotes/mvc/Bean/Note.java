package com.example.mynotes.mvc.Bean;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;//笔记标题
    private String content;//笔记内容
    private long time;//保存日期

    public Note(String title, long time, String content)
    {
        this.title=title;
        this.time=time;
        this.content=content;
    }

    public Note(){

    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title=title;
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
