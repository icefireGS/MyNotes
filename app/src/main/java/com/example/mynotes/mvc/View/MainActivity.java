package com.example.mynotes.mvc.View;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Adapter.noteslistAdapter;
import com.example.mynotes.mvc.Bean.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity  {
    private List<Note> notesList=new ArrayList<>();  //笔记列表
    private noteslistAdapter adapter;  //笔记列表项适配器
    private ListView notesView;   //笔记列表视窗
    private ImageView listnone;   //暂无图片
    //private NoteController controller;  //笔记controller对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNotesList();
        initView();
    }

    void initNotesList(){
        notesView=findViewById(R.id.noteslist);
        //notesList=controller.showNotes();
        Note note1 = new Note();
        note1.setTitle("测试笔记1");
        note1.setContent("测试内容");
        note1.setTime(System.currentTimeMillis());
        //notesList.add(note1);
        //......
        adapter=new noteslistAdapter(this,R.layout.item_noteslist,notesList);
        notesView.setAdapter(adapter);
    }

    void initView(){
        listnone=findViewById(R.id.listnone);

        autosetNoneView(R.drawable.nonote);
    }


    //自动设置暂无图片
    void autosetNoneView(int imageID){
        listnone.setImageResource(imageID);
        if(notesList.isEmpty()){
            listnone.setVisibility(View.VISIBLE);
        } else {
            listnone.setVisibility(View.INVISIBLE);
        }
    }


}
