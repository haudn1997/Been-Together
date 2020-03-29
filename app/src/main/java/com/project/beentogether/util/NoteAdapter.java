package com.project.beentogether.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.beentogether.R;
import com.project.beentogether.model.NoteCalendarModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    ArrayList<NoteCalendarModel> notes;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    public NoteAdapter() {
        FirebaseUtil.openFbReference("notes");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        notes = new ArrayList<>();

        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NoteCalendarModel td = dataSnapshot.getValue(NoteCalendarModel.class);
                td.setId(dataSnapshot.getKey());
                notes.add(td);
                notifyItemInserted(notes.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_rows, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteCalendarModel note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCreatedDate;
        ImageView imageNoteCalendar;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            imageNoteCalendar = itemView.findViewById(R.id.imageNoteCalendar);

        }

        public void bind(NoteCalendarModel note) {
            tvTitle.setText(note.getContentNote());
            tvCreatedDate.setText(note.getDateCreated());
            showImageNoteCalendar(note.getImageUrl());
        }

        private void showImageNoteCalendar(String imageUrl) {
            if (imageUrl != null && imageUrl.isEmpty() == false) {
                Picasso.with(imageNoteCalendar.getContext()).load(imageUrl).into(imageNoteCalendar);
            }
        }
    }
}
