package com.example.mynotes.mvc.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Adapter.noteslistAdapter;
import com.example.mynotes.mvc.Bean.Clock;
import com.example.mynotes.mvc.Bean.Isdel;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Controller.NoteController;
import com.example.mynotes.mvc.Interface.ClickCallBack;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;
import com.github.clans.fab.FloatingActionButton;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
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
    private ImageView querynone;  //无搜索结果
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
    private PopupMenu rightMenu;       //右侧菜单
    private Menu notemenu;           //note列表菜单
    private Menu clockmenu;          //clock列表菜单
    private Button config;        //确认按钮
    private Button cancle;         //取消按钮
    private List<Isdel> isdelList=new ArrayList<>();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
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
        notemenu = new Menu(false, 0);
        switchNotesView();
    }

    void switchNotesView(){
        notemenu.addItem(new MenuItem.Builder().setWidth(160)//单个菜单 button 的宽度
                .setBackground(null)
                .setIcon(getResources().getDrawable(R.drawable.delete))// set icon
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .build());
        notemenu.addItem(new MenuItem.Builder().setWidth(160)//单个菜单 button 的宽度
                .setBackground(null)
                .setIcon(getResources().getDrawable(R.drawable.export))// set icon
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .build());
        notesView.setMenu(notemenu);
        notesView.setAdapter(adapter);
    }

    void initView(){
        listnone=findViewById(R.id.listnone);
        querynone=findViewById(R.id.querynone);
        menu_left=findViewById(R.id.menulist);
        toptile=findViewById(R.id.toptitle);
        menu_right=findViewById(R.id.rightlist);
        searchEdit=findViewById(R.id.searchedit);
        addNote=findViewById(R.id.addnote);
        showNotes=findViewById(R.id.shownotes);
        showClocks=findViewById(R.id.showclock);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
        navigationView = (NavigationView) findViewById(R.id.nav);
        config=findViewById(R.id.config_del);
        cancle=findViewById(R.id.cancle_del);

        Drawable editdraw=getResources().getDrawable(R.drawable.search);
        editdraw.setBounds(0,0,70,70);
        searchEdit.setCompoundDrawables(editdraw,null,null,null);

        autosetNoneView(R.drawable.nonote,true);

        rightMenu = new PopupMenu(MainActivity.this,menu_right);
        rightMenu.getMenuInflater().inflate(R.menu.menu_main, rightMenu.getMenu());
    }

    void initListener() {

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNoteView) {
                    Note newnote = new Note("未命名", System.currentTimeMillis(), "");
                    Intent intent = new Intent(MainActivity.this, showNoteActivity.class);
                    intent.putExtra("note", newnote);
                    intent.putExtra("isedit", true);
                    startActivity(intent);
                }
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
                        notesView.closeSlidedItem();
                        break;
                    case 1:   //导出按钮
                        Toast.makeText(MainActivity.this, "导出按钮", Toast.LENGTH_SHORT).show();
                        notesView.closeSlidedItem();
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
                rightMenu.show();
            }
        });

        rightMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mul_load:
                        Toast.makeText(MainActivity.this, "导入按钮", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mul_delete:
                        adapter.setDelState(true);
                        adapter.notifyDataSetChanged();
                        showNotes.setVisibility(View.INVISIBLE);
                        showClocks.setVisibility(View.INVISIBLE);
                        config.setVisibility(View.VISIBLE);
                        cancle.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isdelList.size()>0){
                    for(int i=0;i<isdelList.size();i++){
                        controller.DeleteControl(notesList.get(isdelList.get(i).isDelcheack()));
                    }
                    Toast.makeText(MainActivity.this, "批量删除成功!", Toast.LENGTH_SHORT).show();
                }
                adapter.setDelState(false);
                updateListView(true);
                showNotes.setVisibility(View.VISIBLE);
                showClocks.setVisibility(View.VISIBLE);
                config.setVisibility(View.INVISIBLE);
                cancle.setVisibility(View.INVISIBLE);
                isdelList.clear();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setDelState(false);
                adapter.notifyDataSetChanged();
                showNotes.setVisibility(View.VISIBLE);
                showClocks.setVisibility(View.VISIBLE);
                config.setVisibility(View.INVISIBLE);
                cancle.setVisibility(View.INVISIBLE);
                isdelList.clear();
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isNoteView) {
                    String query_s = searchEdit.getText().toString();
                    notesList.clear();
                    notesList = controller.FuzzyControl(query_s);
                    if (notesList.isEmpty()) {
                        querynone.setVisibility(View.VISIBLE);
                        listnone.setVisibility(View.INVISIBLE);
                    } else {
                        querynone.setVisibility(View.INVISIBLE);
                    }

                    if (query_s.equals("")) {
                        querynone.setVisibility(View.INVISIBLE);
                        notesList.clear();
                        notesList = controller.showNotes();
                        adapter = new noteslistAdapter(MainActivity.this, R.layout.item_noteslist, notesList, MainActivity.this);
                        notesView.setAdapter(adapter);
                        autosetNoneView(R.drawable.nonote, true);
                    } else {
                        adapter = new noteslistAdapter(MainActivity.this, R.layout.item_noteslist, notesList, MainActivity.this);
                        notesView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNoteView=true;
                toptile.setText("全部笔记");
                searchEdit.setHint("搜索笔记");
                switchNotesView();
                updateListView(true);
                menu_right.setVisibility(View.VISIBLE);
                showNotes.setImageResource(R.drawable.notebook_focuse);
                showClocks.setImageResource(R.drawable.clock_nomal);
            }
        });

        showClocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNoteView=false;
                toptile.setText("全部提醒");
                searchEdit.setHint("搜索提醒");
                //switchClocksView();
                //updateListView(false);
                autosetNoneView(R.drawable.noclock,false);
                menu_right.setVisibility(View.INVISIBLE);
                showNotes.setImageResource(R.drawable.notebook_nomal);
                showClocks.setImageResource(R.drawable.clock_focuse);
                }
        });

        menu_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (drawerLayout.isDrawerOpen(navigationView)) {
                        drawerLayout.closeDrawer(navigationView);
                    } else {
                        drawerLayout.openDrawer(navigationView);
                    }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.upload:
                        Toast.makeText(MainActivity.this, "上传笔记", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.download:
                        Toast.makeText(MainActivity.this, "同步笔记", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        showExitDialog();
                        break;
                }
                return false;
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

    @Override
    public void onCheckClick(int position) {
        Isdel newdel=new Isdel(position);
        isdelList.add(newdel);
    }

    @Override
    public void onCheckCancle(int position) {
        for(int i=isdelList.size()-1;i>=0;i--){
            if(isdelList.get(i).isDelcheack()==position){
                isdelList.remove(i);
            }
        }
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

    void updateListView(boolean isNoteView){
        if(isNoteView) {
            notesList.clear();
            notesList = controller.showNotes();
            adapter.notifyDataSetChanged();
            autosetNoneView(R.drawable.nonote, true);
        } else {
            clocksList.clear();
            //clockList=controller.showClocks();
            //cAdapter.notifyDataSetChanged();
            //autosetNoneView(R.drawable.noclock,false);
        }
    }

    private void showExitDialog(){
        new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                .setTitle("退出")
                .setMessage("确定要退出程序吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

}
