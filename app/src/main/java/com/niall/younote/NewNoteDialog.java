package com.niall.younote;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NewNoteDialog extends AppCompatDialogFragment {
   private EditText nnTag;
   private EditText nnBody;
   private NewNoteDialogListener listener;

   @Override
   public void onAttach(Context context){
       super.onAttach(context);

       try {
           listener = (NewNoteDialogListener) context;
       } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + " must implement NewNoteDialogListener");
       }
   }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
         View view = inflater.inflate(R.layout.layout_dialog, null);

         builder.setView(view)
         .setTitle("New note")
         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

             }
         })
         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

                 String noteTag = nnTag.getText().toString();
                 String noteBody = nnBody.getText().toString();
                 listener.applyTexts(noteTag, noteBody);
             }
         });

            nnTag = view.findViewById(R.id.edit_tag);
            nnBody = view.findViewById(R.id.edit_body);
            return builder.create();

    }

    public interface NewNoteDialogListener{
        void applyTexts(String tag, String body);

    }
}
