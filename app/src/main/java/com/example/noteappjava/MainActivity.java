package com.example.noteappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addNote;
    RecyclerView recyclerView;

    SharedPreferences sharedPreferences;

    static ArrayList<String> title = new ArrayList<String>();
    static ArrayList<String> content = new ArrayList<String>();
    static ArrayList<String> timestamp = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences(
                "com.example.notepadappsharedpreferences",
                MODE_PRIVATE
        );

        HashSet<String> hashSet = (HashSet<String>) sharedPreferences.getStringSet("noteTitle", null);
        HashSet<String> hashSet2 = (HashSet<String>) sharedPreferences.getStringSet("noteBody", null);
        HashSet<String> hashSet3 = (HashSet<String>) sharedPreferences.getStringSet("noteTimestamp", null);
        if(hashSet != null) {
            title = new ArrayList<>(hashSet);
        }
        if(hashSet2 != null) {
            content = new ArrayList<>(hashSet2);
        }
        if(hashSet3 != null) {
            timestamp = new ArrayList<>(hashSet3);
        }


        recyclerView = findViewById(R.id.noteView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter adapter = new noteAdapter(title, content, timestamp, this);
        recyclerView.setAdapter(adapter);


        addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });


    }
}