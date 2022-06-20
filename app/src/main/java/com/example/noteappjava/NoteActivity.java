package com.example.noteappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton done;
    EditText title;
    EditText content;
    SharedPreferences sharedPreferences;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm z");
    String currentDateandTime = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        title = findViewById(R.id.noteTitle);
        content = findViewById(R.id.noteBody);

        String action = getIntent().getStringExtra("action");

        if(Objects.equals(action, "edit")){
            title.setText(getIntent().getStringExtra("title"));
            content.setText(getIntent().getStringExtra("content"));
        }

        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = title.getText().toString();
                String newContent = content.getText().toString();
                String edited;

                sharedPreferences = getApplicationContext().getSharedPreferences(
                        "com.example.notepadappsharedpreferences",
                        MODE_PRIVATE
                );

                if(Objects.equals(action, "edit")){
                    edited = "Edited on : " + currentDateandTime;
                    int index = getIntent().getIntExtra("position", 0);
                    MainActivity.title.set(index, newTitle);
                    MainActivity.content.set(index, newContent);
                    MainActivity.timestamp.set(index, edited);

                }else{
                    edited = "Added on : " + currentDateandTime;
                    MainActivity.title.add(newTitle);
                    MainActivity.content.add(newContent);
                    MainActivity.timestamp.add(edited);
                }

                HashSet<String> hashSet = new HashSet<>(MainActivity.title);
                HashSet<String> hashSet2 = new HashSet<>(MainActivity.content);
                HashSet<String> hashSet3 = new HashSet<>(MainActivity.timestamp);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("noteTitle", hashSet).apply();
                editor.putStringSet("noteBody", hashSet2).apply();
                editor.putStringSet("noteTimestamp", hashSet3).apply();


                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}