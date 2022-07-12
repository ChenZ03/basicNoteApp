package com.example.noteappjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class noteAdapter extends ListAdapter<NoteData, noteAdapter.ViewHolder> {

    public noteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<NoteData> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteData>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteData oldItem, @NonNull NoteData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteData oldItem, @NonNull NoteData newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    private OnItemClickListener listener;
    private OnItemClickListener2 listener2;

    @NonNull
    @Override
    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    // https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup,%20int)
    //This new ViewHolder should be constructed with a new View that can represent the items of the given type. You can either create a new View manually or inflate it from an XML layout file.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);
        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        NoteData noteData = getItem(position);

        viewHolder.title.setText(noteData.getTitle());
        viewHolder.time.setText(noteData.getTimeStamp());
    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = (OnItemClickListener) listener; }
    public interface OnItemClickListener { void onItemClick(NoteData note); }


    public void setOnItemClickListener2(OnItemClickListener2 listener2) { this.listener2 = (OnItemClickListener2) listener2; }
    public interface OnItemClickListener2 { void onItemClick(NoteData note); }

    class ViewHolder extends RecyclerView.ViewHolder {

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

            view.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            imageView.setOnClickListener(v -> {
                if (listener2 != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener2.onItemClick(getItem(position));
                    }
                }
            });
        }


    }

}
