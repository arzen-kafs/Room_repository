package com.example.room_repository;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NoteRepository {

    private NoteDoa noteDoa;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database=NoteDatabase.getInstance(application);
        noteDoa=database.noteDoa();
        allNotes=noteDoa.getAllNotes();

    }

    public void insert(Note note){
            new InsertNoteAsyncTask(noteDoa).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDoa).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDoa).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDoa).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
       private NoteDoa noteDoa;
       private UpdateNoteAsyncTask(NoteDoa noteDoa){
           this.noteDoa=noteDoa;
       }
        @Override
        protected Void doInBackground(Note... notes) {
           noteDoa.update(notes[0]);
           return null;
        }
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDoa noteDoa;
        private InsertNoteAsyncTask(NoteDoa noteDoa){
            this.noteDoa=noteDoa;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDoa.insert(notes[0]);
            return null;
        }
    }


    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDoa noteDoa;
        private DeleteNoteAsyncTask(NoteDoa noteDoa){
            this.noteDoa=noteDoa;
    }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDoa.delete(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDoa noteDoa;
        private DeleteAllNoteAsyncTask(NoteDoa noteDoa){
            this.noteDoa=noteDoa;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDoa.deleteAllNotes();
            return null;
        }
    }
}
