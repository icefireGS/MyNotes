package com.example.mynotes.mvc.Bean;

public class MyDate {
    private String my_year;//年
    private String my_month;//月
    private String my_day;//日
    private String my_minute;//分

    public MyDate(String m_year,String m_month,String m_day,String m_minute)
    {
        this.my_year=m_year;
        this.my_month=m_month;
        this.my_day=m_day;
        this.my_minute=m_minute;
    }

    public MyDate()
    {
        this.my_year=null;
        this.my_month=null;
        this.my_day=null;
        this.my_minute=null;
    }

    public String GetYear()//获取年
    {
        return my_year;
    }

    public String GetMonth()//获取月
    {
        return my_month;
    }

    public String GetDay()//获取日
    {
        return  my_day;
    }

    public String GetMinute()//获取分
    {
        return  my_minute;
    }

    public void SetYear(String m_year)//设置年
    {
        this.my_year=m_year;
    }

    public void SetMonth(String m_month)//设置月
    {
        this.my_month=m_month;
    }

    public void SetDay(String m_day)//设置日
    {
        this.my_day=m_day;
    }

    public void SetMinute(String m_minute)//设置分
    {
        this.my_minute=m_minute;
    }
}
