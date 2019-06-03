package com.example.mynotes.mvc.View;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Controller.NoteController;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;

import java.text.SimpleDateFormat;

public class showNoteActivity extends Activity {
    private ImageButton back;    //返回按钮
    private TextView title;      //标题
    private ImageButton save_edit;  //编辑保存按钮
    private TextView addTtile; //添加标题
    private TextView time;   //日期
    private boolean isEdit;     //是否编辑界面
    private EditText editnote;  //笔记编辑框
    private NoteController controller; //笔记controller对象
    private NoteDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        initView();
    }

    void initView(){
        back=findViewById(R.id.backbutton);
        title=findViewById(R.id.notetitle);
        save_edit=findViewById(R.id.savebutton);
        addTtile=findViewById(R.id.addtitle);
        time=findViewById(R.id.showtime);
        editnote=findViewById(R.id.editnote);
        dbHelper=new NoteDataBaseHelper(this,"db",1);
        controller=new NoteController(dbHelper);

        Intent intent=getIntent();
        Note intentNote=(Note)intent.getSerializableExtra("note");
        isEdit=intent.getBooleanExtra("isedit",false);

        title.setText(intentNote.getTitle());

        if(title.getText().toString().equals("未命名")){
            addTtile.setText("添加标题  ▼");
        } else {
            addTtile.setText("修改标题  ▼");
        }

        if(isEdit){
            save_edit.setImageResource(R.drawable.right_save);
            editnote.setFocusableInTouchMode(true);
            editnote.setFocusable(true);
            editnote.requestFocus();
            addTtile.setVisibility(View.VISIBLE);
        }else{
            save_edit.setImageResource(R.drawable.right_edit);
            editnote.setFocusable(false);
            editnote.setFocusableInTouchMode(false);
            addTtile.setVisibility(View.INVISIBLE);
        }

        SimpleDateFormat settime = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        String lasttime=settime.format(intentNote.getTime());
        time.setText(lasttime);

        editnote.setText(intentNote.getContent());
    }

    void initListener(){

        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    Note newnote=new Note(title.getText().toString(),System.currentTimeMillis(),editnote.getText().toString());
                    controller.AddControl(newnote);

                    isEdit=false;
                }else{
                    isEdit=true;
                }
            }
        });
    }

}
