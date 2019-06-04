package com.example.mynotes.mvc.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Adapter.noteslistAdapter;
import com.example.mynotes.mvc.Bean.Clock;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Controller.NoteController;
import com.example.mynotes.mvc.Interface.ClickCallBack;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;
import com.github.clans.fab.FloatingActionButton;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements ClickCallBack {
    private List<Note> notesList=new ArrayList<>();  //笔记列表
    private List<Clock>  clocksList=new ArrayList<>();  //闹钟列表
    private noteslistAdapter adapter;  //笔记列表项适配器
    private SlideAndDragListView notesView;   //笔记列表视窗
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
    private MessageReceiver mr;       //广播注册类
    private NoteDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNotesList();
        initView();
        initListener();
        initBroadcastReceiver();
    }

    void initNotesList(){
        notesView=findViewById(R.id.noteslist);
        dbHelper=new NoteDataBaseHelper(this,"db",1);
        controller=new NoteController(dbHelper);
        notesList.clear();
        notesList=controller.showNotes();
        adapter=new noteslistAdapter(this,R.layout.item_noteslist,notesList,this);

        Menu menu = new Menu(false, 0);
        menu.addItem(new MenuItem.Builder().setWidth(160)//单个菜单 button 的宽度
                .setBackground(null)
                .setIcon(getResources().getDrawable(R.drawable.delete))// set icon
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .build());
        menu.addItem(new MenuItem.Builder().setWidth(160)//单个菜单 button 的宽度
                .setBackground(null)
                .setIcon(getResources().getDrawable(R.drawable.export))// set icon
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .build());
        notesView.setMenu(menu);
        notesView.setAdapter(adapter);
    }

    void initView(){
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

        notesView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (buttonPosition){
                    case 0: //删除按钮
                        Note deletenote=notesList.get(itemPosition);
                        if(controller.DeleteControl(deletenote)){
                            notesList.clear();
                            notesList=controller.showNotes();
                            adapter.notifyDataSetChanged();
                            autosetNoneView(R.drawable.nonote,true);
                            Toast.makeText(MainActivity.this, "删除成功!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "删除失败!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:   //导出按钮

                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return Menu.ITEM_NOTHING;
            }
        });

        menu_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    void initBroadcastReceiver(){
        mr= new MessageReceiver();          // 广播注册类
        IntentFilter filter = new IntentFilter();           // 过滤器
        filter.addAction("action.refresh");
        registerReceiver(mr, filter);               // 注册进去
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mr);
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(MainActivity.this,showNoteActivity.class);
        intent.putExtra("note",notesList.get(position));
        intent.putExtra("isedit",false);
        startActivity(intent);
    }



    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("action.refresh")){          // 如果是该广播，则执行XXXX操作
                notesList.clear();
                notesList=controller.showNotes();
                adapter.notifyDataSetChanged();
                autosetNoneView(R.drawable.nonote,true);
            }
        }
    }


}
