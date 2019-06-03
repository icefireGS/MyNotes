package com.example.mynotes.mvc.Controller;

import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Model.NoteDataBaseHelper;
import com.example.mynotes.mvc.Model.NoteModel;
import java.util.*;

public class NoteController {
    private NoteModel mode;

    public NoteController(NoteDataBaseHelper dbHelper)
    {
        mode=new NoteModel(dbHelper);
    }

    public boolean AddControl(Note addnote)
    {
        return mode.addNote(addnote.getTitle(),addnote.getContent(),addnote.getTime());
    }

    public List<Note> showNotes()
    {
        List<Note> noteList=mode.showNotes();
        return noteList;
    }

    public boolean  DeleteControl(Note deletenote)
    {
        if(mode.showNotes().isEmpty())
        {
            return false;
        } else {
            return mode.deleteNote(deletenote.getTitle(),deletenote.getTime());
        }

    }

}
