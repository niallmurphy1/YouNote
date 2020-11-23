package com.niall.younote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niall.younote.entities.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<Note> aNoteList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView tagText;
        public TextView bodyText;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            tagText = itemView.findViewById(R.id.tagTextView);
            bodyText = itemView.findViewById(R.id.bodyTextView);
        }
    }
    public ExampleAdapter(ArrayList<Note> noteArrayList) {
        aNoteList = noteArrayList;
    }
    @NotNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_element, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Note currentNote = aNoteList.get(position);
        holder.mImageView.setImageResource(currentNote.getImage());
        holder.tagText.setText(currentNote.getTag());
        holder.bodyText.setText(currentNote.getBody());
    }
    @Override
    public int getItemCount() {
        return aNoteList.size();
    }
}
