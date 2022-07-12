package com.example.noteappjava;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(NoteData noteData);
    @Update
    void update(NoteData noteData);
    @Delete
    void delete(NoteData noteData);
    @Query("DELETE FROM note")
    void deleteAll();
    @Query("SELECT * FROM note")
    LiveData<List<NoteData>> getAll();
}
