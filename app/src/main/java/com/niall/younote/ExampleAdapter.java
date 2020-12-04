package com.niall.younote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niall.younote.entities.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {

    private ArrayList<Note> myList;

    private OnItemClickListener mListener;

    private ArrayList<Note> noteListFull;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView tagText;
        public TextView bodyText;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            tagText = itemView.findViewById(R.id.tagTextView);
            bodyText = itemView.findViewById(R.id.bodyTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(listener != null){
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                listener.onItemClick(position);
                            }
                        }

                }
            });
        }
    }
    public ExampleAdapter(ArrayList<Note> myList) {
        this.myList = myList;
        noteListFull = new ArrayList<>(myList);
    }
    @NotNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_element, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Note currentNote = myList.get(position);
        if(currentNote.isChecked()){
            holder.mImageView.setImageResource(R.drawable.ic_tick);
        }
        else if(!currentNote.isChecked()){
            holder.mImageView.setImageResource(R.drawable.ic_right_arrow);
        }
        else {
            holder.mImageView.setImageResource(R.drawable.ic_right_arrow);
        }
        holder.tagText.setText(currentNote.getTag());
        holder.bodyText.setText(currentNote.getBody());
    }
    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Note> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(noteListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note note:noteListFull){
                    if(note.getTag().toLowerCase().contains(filterPattern)){
                        filteredList.add(note);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            myList.clear();
            myList.addAll((ArrayList) results.values);
//            for(int i = 0; i < myList.size(); i++){
//                if(myList.get(i).get)
//            }
            notifyDataSetChanged();
        }
    };
}
