package com.example.room_repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NoteDoa {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

   @Query("Delete From note_table")
    void deleteAllNotes();

    @Query("Select * From note_table Order by priority desc")
    LiveData<List<Note>> getAllNotes();
}
