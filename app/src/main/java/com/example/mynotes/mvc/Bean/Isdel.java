package com.example.mynotes.mvc.Bean;

public class Isdel {
    int delcheack;

    public Isdel(int position){
        delcheack=position;
    }

    public int isDelcheack() {
        return delcheack;
    }

    public void setDelcheack(int delcheack) {
        this.delcheack = delcheack;
    }
}
