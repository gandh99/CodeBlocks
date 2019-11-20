package com.gandh99.codeblocks.dashboard;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gandh99.codeblocks.R;

public class AddProjectDialog extends DialogFragment {

  public AddProjectDialog() {
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    EditText editTextTitle, editTextLeader, editTextDescription;
    Button buttonCreateProject;

    View view =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_project, null);

    editTextTitle = view.findViewById(R.id.dialog_project_title);
    editTextLeader = view.findViewById(R.id.dialog_project_leader);
    editTextDescription = view.findViewById(R.id.dialog_project_description);
    buttonCreateProject = view.findViewById(R.id.dialog_project_create);

    buttonCreateProject.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    return new AlertDialog.Builder(getActivity())
      .setView(view)
      .create();
  }
}
