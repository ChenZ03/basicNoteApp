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

import com.example.noteappjava.databinding.ActivityNoteViewBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton done;
    EditText title;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm z");
    String currentDateandTime = sdf.format(new Date());
    ActivityNoteViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        binding = ActivityNoteViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        title = binding.noteTitle;
        EditText content = binding.noteBody;

        String action = getIntent().getStringExtra("action");

        if(Objects.equals(action, "edit")){
            title.setText(getIntent().getStringExtra("title"));
            content.setText(getIntent().getStringExtra("content"));
        }

        done = binding.done;
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = title.getText().toString();
                String newContent = content.getText().toString();
                String edited;


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



                Intent intent = new Intent(NoteActivity.this, MainActivity.class);
                intent.putExtra("title", newTitle);
                intent.putExtra("content", newContent);
                intent.putExtra("timeStamp", edited);
                int id = getIntent().getIntExtra("id",-1);
                if (id != -1){
                    intent.putExtra("id",id);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}