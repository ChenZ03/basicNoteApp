package com.example.noteappjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;


public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    private ArrayList<String> title ;
    private ArrayList<String> content ;
    private ArrayList<String> timestamp ;
    private SharedPreferences sharedPreferences;
    private Context context;

    public noteAdapter(ArrayList<String> title, ArrayList<String> content, ArrayList<String> timestamp, Context context) {
        this.title = title;
        this.content = content;
        this.context = context;
        this.timestamp = timestamp;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public TextView time;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.noteTitle);
            imageView = (ImageView) view.findViewById(R.id.delete);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            time = (TextView) view.findViewById(R.id.timestamp);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.note_row, parent, false);
        return new ViewHolder(listItem);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.title.setText(title.get(position));
        viewHolder.time.setText(timestamp.get(position));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.remove(position);
                content.remove(position);
                sharedPreferences = context.getSharedPreferences("com.example.notepadappsharedpreferences", Context.MODE_PRIVATE);
                HashSet<String> hashSet = new HashSet<String>(MainActivity.title);
                HashSet<String> hashSet2 = new HashSet<String>(MainActivity.content);
                sharedPreferences.edit().putStringSet("noteTitle", hashSet).apply();
                sharedPreferences.edit().putStringSet("noteBody", hashSet2).apply();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, title.size());
            }
        });
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteActivity.class);
                intent.putExtra("title", title.get(position));
                intent.putExtra("position", position);
                intent.putExtra("content", content.get(position));
                intent.putExtra("action", "edit");
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return title.size();
    }
}
