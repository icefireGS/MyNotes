package com.example.mynotes.mvc.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Controller.NoteController;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.text.SimpleDateFormat;

public class showNoteActivity extends Activity {
    private ImageButton back;    //返回按钮
    private TextView title;      //标题
    private ImageButton save_edit;  //编辑保存按钮
    private TextView addTtile; //添加标题
    private TextView time;   //日期
    private boolean isEdit;     //是否编辑界面
    private EditText editnote;  //笔记编辑框
    private TextView uid;   //笔记标志
    private ImageView T;       //T图标
    private Note Pnote;
    private NoteController controller; //笔记controller对象
    private NoteDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        initView();
        initListener();
    }

    void initView(){
        back=findViewById(R.id.backbutton);
        title=findViewById(R.id.notetitle);
        save_edit=findViewById(R.id.savebutton);
        addTtile=findViewById(R.id.addtitle);
        time=findViewById(R.id.showtime);
        editnote=findViewById(R.id.editnote);
        uid=findViewById(R.id.uid);
        T=findViewById(R.id.Timage);
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
            T.setVisibility(View.VISIBLE);
        }else{
            save_edit.setImageResource(R.drawable.right_edit);
            editnote.setFocusable(false);
            editnote.setFocusableInTouchMode(false);
            addTtile.setVisibility(View.INVISIBLE);
            T.setVisibility(View.INVISIBLE);
        }

        SimpleDateFormat settime = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        String lasttime=settime.format(intentNote.getTime());
        time.setText(lasttime);
        uid.setText(String.valueOf(intentNote.getTime()));
        editnote.setText(intentNote.getContent());

        Pnote=new Note(title.getText().toString(),Long.parseLong(uid.getText().toString()),editnote.getText().toString());
    }

    void initListener(){

        save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    long nowtime=System.currentTimeMillis();
                    Note newnote = new Note(title.getText().toString(), nowtime, editnote.getText().toString());
                    uid.setText(String.valueOf(nowtime));

                    if(!controller.IsExist(Pnote)) {
                        controller.AddControl(newnote);
                    } else {
                        controller.UpdateControl(Pnote,newnote);
                    }

                    isEdit=false;
                    save_edit.setImageResource(R.drawable.right_edit);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    editnote.setFocusable(false);
                    editnote.setFocusableInTouchMode(false);
                    addTtile.setVisibility(View.INVISIBLE);
                    T.setVisibility(View.INVISIBLE);
                    Toast.makeText(showNoteActivity.this, "保存成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("action.refresh");    // 引号中是标识这条广播的名字
                    sendBroadcast(intent);
                }else{
                    isEdit=true;
                    save_edit.setImageResource(R.drawable.right_save);
                    editnote.setFocusableInTouchMode(true);
                    editnote.setFocusable(true);
                    editnote.requestFocus();
                    addTtile.setVisibility(View.VISIBLE);
                    T.setVisibility(View.VISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addTtile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddtitleDialog();
            }
        });
    }

    void showAddtitleDialog(){
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(showNoteActivity.this);
        builder.setTitle("输入标题")
                .setPlaceholder("在此输入新标题")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString();
                        if (text.length() > 0) {
                            title.setText(text);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(showNoteActivity.this, "请填入标题", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
}
