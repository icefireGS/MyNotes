package com.example.mynotes.mvc.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Adapter.noteslistAdapter;
import com.example.mynotes.mvc.Bean.Clock;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Controller.NoteController;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity  {
    private List<Note> notesList=new ArrayList<>();  //笔记列表
    private List<Clock>  clocksList=new ArrayList<>();  //闹钟列表
    private noteslistAdapter adapter;  //笔记列表项适配器
    private ListView notesView;   //笔记列表视窗
    private ImageView listnone;   //暂无图片
    private boolean isNoteView;   //笔记界面或闹钟界面标志
    private ImageButton menu_left;    //左侧菜单
    private TextView toptile;     //标题栏
    private ImageButton menu_right;  //右侧菜单
    private EditText searchEdit;   //搜索栏
    private FloatingActionButton addNote;  //添加笔记
    private ImageButton showNotes;   //显示笔记列表
    private ImageButton showClocks;   //显示闹钟列表
    private NoteController controller; //笔记controller对象
    private NoteDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNotesList();
        initListener();
    }

    void initNotesList(){
        dbHelper=new NoteDataBaseHelper(this,"db",1);
        controller=new NoteController(dbHelper);
        notesList=controller.showNotes();
        adapter=new noteslistAdapter(this,R.layout.item_noteslist,notesList);
        notesView.setAdapter(adapter);
    }

    void initView(){
        notesView=findViewById(R.id.noteslist);
        listnone=findViewById(R.id.listnone);
        menu_left=findViewById(R.id.menulist);
        toptile=findViewById(R.id.toptitle);
        menu_right=findViewById(R.id.rightlist);
        searchEdit=findViewById(R.id.searchedit);
        addNote=findViewById(R.id.addnote);
        showNotes=findViewById(R.id.shownotes);
        showClocks=findViewById(R.id.showclock);

        Drawable editdraw=getResources().getDrawable(R.drawable.search);
        editdraw.setBounds(0,0,70,70);
        searchEdit.setCompoundDrawables(editdraw,null,null,null);

        autosetNoneView(R.drawable.nonote,true);
    }

    void initListener() {

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newnote=new Note("未命名",System.currentTimeMillis(),"");
                Intent intent=new Intent(MainActivity.this,showNoteActivity.class);
                intent.putExtra("note",newnote);
                intent.putExtra("isedit",true);
                startActivity(intent);
            }
        });

    }

    //自动设置暂无图片
    void autosetNoneView(int imageID,boolean setIsNoteView){
        isNoteView=setIsNoteView;
        listnone.setImageResource(imageID);
        if(isNoteView) {
            if (notesList.isEmpty()) {
                listnone.setVisibility(View.VISIBLE);
            } else {
                listnone.setVisibility(View.INVISIBLE);
            }
        } else {
            if (clocksList.isEmpty()) {
                listnone.setVisibility(View.VISIBLE);
            } else {
                listnone.setVisibility(View.INVISIBLE);
            }
        }

    }


}
