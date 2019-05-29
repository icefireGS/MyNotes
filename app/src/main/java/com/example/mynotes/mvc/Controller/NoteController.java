package com.example.mynotes.mvc.Controller;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Interface.MainViewCallBack;
import com.example.mynotes.mvc.Interface.NoteViewCallBack;
import com.example.mynotes.mvc.Model.NoteModel;
import java.util.*;

public class NoteController {
    private MainViewCallBack mc;
    private NoteViewCallBack nc;
    private NoteModel mode;
    boolean show=false;

    public NoteController(MainViewCallBack mc, NoteViewCallBack nc)
    {
        this.mc=mc;
        this.nc=nc;
        mode=new NoteModel();
    }

    public boolean add(onAddNoteListener listener)
    {
        Note newnote=nc.getNote();
     mode.addNote(newnote.getTitle(),newnote.getContent(),newnote.getTime());
      if(listener!=null)
      {
        listener.onComplete();
        return true;
      }
      else
      {
          listener.onError();
          return false;
      }
    }
    public List<Note> show(onShowNoteListener listener)
    {

        List<Note> noteList=mode.showNotes();
        if(listener!=null)
        {
            listener.onComplete();
            show=true;
        }
        else
        {
            listener.onError();
            show=false;
        }
          return noteList;
    }
    public boolean  delete(onDeleteNoteListener listener)
    {
        Note newnote=mc.getDeleteNote();
        mode.deleteNote(newnote.getTitle());
        if(mode.showNotes().isEmpty())
        {
            return false;
        }
        if(listener!=null)
        {
            listener.onComplete();
            return true;
        }
        else
        {
            listener.onError();
            return false;
        }
    }
    public interface onAddNoteListener
    {
     void onComplete();
     void onError();
    }
    public interface onShowNoteListener
    {
        void onComplete();
        void onError();
    }
    public interface onDeleteNoteListener
    {
        void onComplete();
        void onError();
    }
}
