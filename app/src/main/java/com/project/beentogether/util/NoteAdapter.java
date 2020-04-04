package com.project.beentogether.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.project.beentogether.activity.CreateNoteCalendarActivity;
import com.project.beentogether.activity.NoteCalendarActivity;
import com.project.beentogether.model.NoteCalendarModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    ArrayList<NoteCalendarModel> notes;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    Context ctx;
    private NoteCalendarModel note;

    public NoteAdapter(Context ctx) {
        this.ctx = ctx;
        note = new NoteCalendarModel();
        FirebaseUtil.openFbReference("notes");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        notes = FirebaseUtil.mNotes;

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

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvCreatedDate, txtContentDialog, txtCreatedDateDialog;
        ImageView imageNoteCalendar, imageNoteCalendarDialog;
        Button btnCloseDialog, btnDeleteDialog, btnEditDialog;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            imageNoteCalendar = itemView.findViewById(R.id.imageNoteCalendar);
            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            note = notes.get(position);
            showDialog(v);
        }

        private void showDialog(View v) {
            final Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(R.layout.show_list_note_calendar_dialog);

            initParametersInDialog(dialog);
            passValuesToDialog();

            btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnDeleteDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseReference.child(note.getId()).removeValue();
                    Intent intent = new Intent(ctx, NoteCalendarActivity.class);
                    ctx.startActivity(intent);
                }
            });

            btnEditDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, CreateNoteCalendarActivity.class);
                    intent.putExtra("Note", note);
                    ctx.startActivity(intent);
                }
            });

            dialog.show();
        }

        private void passValuesToDialog() {
            txtContentDialog.setText(note.getContentNote());
            txtCreatedDateDialog.setText(note.getDateCreated());
            Picasso.with(imageNoteCalendarDialog.getContext()).load(note.getImageUrl()).into(imageNoteCalendarDialog);
        }

        private void initParametersInDialog(Dialog dialog) {
            txtContentDialog = dialog.findViewById(R.id.txtContentDialog);
            txtCreatedDateDialog = dialog.findViewById(R.id.txtCreatedDateDialog);
            imageNoteCalendarDialog = dialog.findViewById(R.id.imageNoteCalendarDialog);

            btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);
            btnDeleteDialog = dialog.findViewById(R.id.btnDeleteDialog);
            btnEditDialog = dialog.findViewById(R.id.btnEditDialog);
        }
    }
}
