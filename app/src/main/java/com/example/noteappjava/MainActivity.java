package com.example.noteappjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.noteappjava.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addNote;
    RecyclerView recyclerView;
    ActivityMainBinding binding;

    private viewModel noteViewModel;
    public static final int ADD_NOTE_REQ = 1;
    public static final int EDIT_NOTE_REQ = 2;

    static ArrayList<String> title = new ArrayList<String>();
    static ArrayList<String> content = new ArrayList<String>();
    static ArrayList<String> timestamp = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.noteView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter adapter = new noteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(viewModel.class);
        noteViewModel.getAll().observe(this, new Observer<List<NoteData>>() {
            @Override
            public void onChanged(List<NoteData> notes) {
                adapter.submitList(notes);
            }
        });



        addNote = binding.addNote;
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("action", "add");
                startActivityForResult(intent, ADD_NOTE_REQ);
            }
        });

        adapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("action", "edit");
            intent.putExtra("title", note.getTitle());
            intent.putExtra("id" , note.getId());
            intent.putExtra("content", note.getDescription());
            intent.putExtra("timeStamp", note.getTimeStamp());
            startActivityForResult(intent, EDIT_NOTE_REQ);
        });

        adapter.setOnItemClickListener2(note -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete Note");
            builder.setMessage("Are you sure you want to delete this note?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                noteViewModel.delete(note);
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQ && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String timestamp = data.getStringExtra("timeStamp");
            NoteData note = new NoteData(title, content, timestamp);
            noteViewModel.insert(note);
        }
        else if(resultCode == RESULT_OK && requestCode == EDIT_NOTE_REQ) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String timestamp = data.getStringExtra("timeStamp");
            int id = data.getIntExtra("id", -1);
            NoteData note = new NoteData(title, content, timestamp);
            note.setId(id);
            noteViewModel.update(note);
        }
    }



}