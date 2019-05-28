package com.example.mynotes.mvc.Controller;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Model.NoteModel;
import java.util.*;
public class NoteController {
    private NoteModel mode;


    public NoteController()
    {
        mode=new NoteModel();
    }
    public boolean add(onAddNoteListener listener)
    {
        myBinder binder=new myBinder();
        Note newnote=binder.getNote();
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
    public boolean show(onShowNoteListener listener)
    {
        mode.showNotes();
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
    public boolean  delete(onDeleteNoteListener listener)
    {
        myBinder binder=new myBinder();
        Note newnote=binder.getNote();
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
