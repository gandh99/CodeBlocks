package com.gandh99.codeblocks.projectPage.tasks.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gandh99.codeblocks.projectPage.GenericTaskAdapter;

public class FilterTaskDialog extends DialogFragment {
  private GenericTaskAdapter adapter;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return super.onCreateDialog(savedInstanceState);
  }

  public void setGenericTaskAdapter(GenericTaskAdapter adapter) {
    this.adapter = adapter;
  }

}
