package com.example.noteappjava;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class viewModel extends AndroidViewModel {
    private repository repository;
    private LiveData<List<NoteData>> allNotes;
    public viewModel(@NonNull Application application) {
        super(application);
        repository = new repository(application);
        allNotes = repository.getAll();
    }
    public void insert(NoteData note) {
        repository.insert(note);
    }
    public void update(NoteData note) {
        repository.update(note);
    }
    public void delete(NoteData note) {
        repository.delete(note);
    }
    public LiveData<List<NoteData>> getAll() {
        return allNotes;
    }
}

