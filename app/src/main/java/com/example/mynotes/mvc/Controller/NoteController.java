package com.example.mynotes.mvc.Controller;

import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Model.NoteModel;
import java.util.*;

public class NoteController {
    private NoteModel mode;
    boolean show=false;

    public NoteController()
    {
        mode=new NoteModel();
    }

    public boolean AddControl(Note addnote)
    {
        return mode.addNote(addnote.getTitle(),addnote.getContent(),addnote.getTime());
    }

    public List<Note> show()
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
            return mode.deleteNote(deletenote.getTitle());
        }

    }

}
