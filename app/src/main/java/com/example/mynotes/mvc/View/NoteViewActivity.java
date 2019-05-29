package com.example.mynotes.mvc.View;

import android.os.Bundle;
import android.app.Activity;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Interface.NoteViewCallBack;

public class NoteViewActivity extends Activity implements NoteViewCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
    }

    @Override
    public Note getNote() {
        return null;
    }
}
