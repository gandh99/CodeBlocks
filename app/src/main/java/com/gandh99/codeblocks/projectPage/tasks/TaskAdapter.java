package com.gandh99.codeblocks.projectPage.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.nfc.FormatException;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.common.dateFormatting.DatePortion;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
  private static final String TAG = "TaskAdapter";
  private List<Task> taskList = new ArrayList<>();
  private Map<String, Integer> priorityMap = new HashMap<>();
  private OnContextMenuItemSelectedListener listener;

  public TaskAdapter() {}

  @NonNull
  @Override
  public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_task, parent, false);

    return new TaskViewHolder(view);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    Task task = taskList.get(position);
    String deadline;

    // Format some of the dates to be displayed
    deadline = CustomDateFormatter.getFormattedDate(task.getDeadline());

    holder.priorityColour.setBackgroundColor(priorityMap.get(task.getPriority()));
    holder.textViewTitle.setText(task.getTitle());
    holder.textViewDescription.setText(task.getDescription());
    holder.chipDeadlineCountdown.setText(deadline);
  }

  @Override
  public int getItemCount() {
    return taskList.size();
  }

  public void updateList(@Nullable List<Task> list) {
    taskList = list;
    notifyDataSetChanged();
  }

  public List<Task> getTaskList() { return taskList; }

  public void setPriorityTypes(Resources resources) {
    String[] priority = resources.getStringArray(R.array.priority);
    priorityMap.put(priority[0], ResourcesCompat.getColor(resources, R.color.colorWhite, null));
    priorityMap.put(priority[1], ResourcesCompat.getColor(resources, R.color.colorGreen, null));
    priorityMap.put(priority[2], ResourcesCompat.getColor(resources, R.color.colorOrange, null));
    priorityMap.put(priority[3], ResourcesCompat.getColor(resources, R.color.colorRed, null));
  }

  class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener {
    View priorityColour;
    TextView textViewTitle, textViewDescription;
    Chip chipDeadlineCountdown;
    ImageView imageViewActions;
    MenuItem editTask, commentTask, markAsDoneTask;

    TaskViewHolder(@NonNull View itemView) {
      super(itemView);

      priorityColour = itemView.findViewById(R.id.priority_colour);
      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
      chipDeadlineCountdown = itemView.findViewById(R.id.list_item_task_deadline_countdown);
      imageViewActions = itemView.findViewById(R.id.list_item_task_actions);

      itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
      contextMenu.setHeaderTitle("Select an option");
      editTask = contextMenu.add("Edit");
      commentTask = contextMenu.add("Comment");
      markAsDoneTask = contextMenu.add("Mark as done");
      editTask.setOnMenuItemClickListener(this);
      commentTask.setOnMenuItemClickListener(this);
      markAsDoneTask.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
      int position = getAdapterPosition();

      // Check for any errors
      if (listener == null || position == RecyclerView.NO_POSITION) {
        return false;
      }

      if (menuItem == editTask) {
        Log.d(TAG, "onMenuItemClick: " + "editTask");
      } else if (menuItem == commentTask) {
        Log.d(TAG, "onMenuItemClick: " + "commentTask");
      } else if (menuItem == markAsDoneTask) {
        listener.onMarkTaskAsDoneSelected(taskList.get(position));
      }

      return false;
    }
  }

  public interface OnContextMenuItemSelectedListener {
    void onMarkTaskAsDoneSelected(Task task);
  }

  public void setOnContextMenuItemSelectedListener(OnContextMenuItemSelectedListener listener) {
    this.listener = listener;
  }

}
