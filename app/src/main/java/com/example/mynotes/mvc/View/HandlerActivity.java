package com.example.mynotes.mvc.View;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.example.mynotes.R;

public class HandlerActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_handler);
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            intent = new Intent(HandlerActivity.this, MainActivity.class);
            startActivity(intent);
            //执行一次后销毁本页面
            finish();
        }
    };

}
