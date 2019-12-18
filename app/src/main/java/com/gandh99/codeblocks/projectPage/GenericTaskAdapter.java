package com.gandh99.codeblocks.projectPage;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
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
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.Base64EncoderDecoder;
import com.gandh99.codeblocks.common.RandomColourGenerator;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.projectPage.members.api.ProjectMember;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public abstract class GenericTaskAdapter extends RecyclerView.Adapter<GenericTaskAdapter.GenericTaskViewHolder> {
  private static final String TAG = "GenericTaskAdapter";
  private Context context;
  private List<Task> taskList = new ArrayList<>();
  private Map<String, Integer> priorityMap = new HashMap<>();
  private OnContextMenuItemSelectedListener listener;
  private Map<String, String> usernameProfilePictureMap = new HashMap<>();

  public GenericTaskAdapter() {}

  @NonNull
  @Override
  public GenericTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
      LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.list_item_task, parent, false);

    return new GenericTaskViewHolder(view);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onBindViewHolder(@NonNull GenericTaskViewHolder holder, int position) {
    Task task = taskList.get(position);
    String deadline;

    // Format some of the dates to be displayed
    deadline = CustomDateFormatter.getFormattedDate(task.getDeadline());

    holder.priorityColour.setBackgroundColor(priorityMap.get(task.getPriority()));
    holder.textViewTitle.setText(task.getTitle());
    holder.textViewDescription.setText(task.getDescription());
    holder.chipDeadlineCountdown.setText(deadline);

    // Display assignees in the ChipGroup. CLEAR CHIPGROUP FIRST!!
    holder.chipGroupAssignees.removeAllViews();
    try {
      if (task.getAssignees().length == 0) {
        holder.chipGroupAssignees.setVisibility(View.GONE);
      } else {
        holder.chipGroupAssignees.setVisibility(View.VISIBLE);
      }

      // Set the chips for the assignees
      for (String assignee : task.getAssignees()) {
        Chip chip = new Chip(context);
        chip.setText(assignee);

        // Set the profile picture of the user in the chip icon
        String profilePicture = usernameProfilePictureMap.get(assignee);
        if (profilePicture.equals("")) {
        chip.setChipIcon(context.getDrawable(R.drawable.ic_account_circle_blue_60dp));
        } else {
          RoundedBitmapDrawable drawable =
            Base64EncoderDecoder.toRoundedBitmapDrawable(context.getResources(), profilePicture);
          chip.setChipIcon(drawable);
        }

        holder.chipGroupAssignees.addView(chip);
      }
    } catch (NullPointerException e) {
      // This might occur if a task has 0 assignees
    }

    // Display categories in the ChipGroup. CLEAR CHIPGROUP FIRST!!
    holder.chipGroupTaskCategories.removeAllViews();
    try {
      if (task.getTaskCategories().length == 0) {
        holder.chipGroupTaskCategories.setVisibility(View.GONE);
      } else {
        holder.chipGroupTaskCategories.setVisibility(View.VISIBLE);
      }

      // Set the chips for the categories
      for (String category : task.getTaskCategories()) {
        Chip chip = new Chip(context);
        chip.setText(category);
        chip.setChipBackgroundColorResource(RandomColourGenerator.getRandomColour(category));
        holder.chipGroupTaskCategories.addView(chip);
      }
    } catch (NullPointerException e) {
      // This might occur if a task has 0 categories
    }
  }

  public void setProfilePictures(List<ProjectMember> projectMembers) {
    for (ProjectMember member : projectMembers) {
      String profilePicture = member.getProfilePicture();
      usernameProfilePictureMap.put(member.getUsername(), profilePicture);
    }
  }

  public void setContext(Context context) {
    this.context = context;
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

  class GenericTaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener {
    View priorityColour;
    TextView textViewTitle, textViewDescription;
    Chip chipDeadlineCountdown;
    ChipGroup chipGroupAssignees, chipGroupTaskCategories;
    ImageView imageViewActions;
    MenuItem editTask, markAsDoneTask;

    GenericTaskViewHolder(@NonNull View itemView) {
      super(itemView);

      priorityColour = itemView.findViewById(R.id.priority_colour);
      textViewTitle = itemView.findViewById(R.id.list_item_task_title);
      textViewDescription = itemView.findViewById(R.id.list_item_task_description);
      chipDeadlineCountdown = itemView.findViewById(R.id.list_item_task_deadline_countdown);
      chipGroupAssignees = itemView.findViewById(R.id.chipgroup_task_assigned_members);
      chipGroupTaskCategories = itemView.findViewById(R.id.chipgroup_task_categories);
      imageViewActions = itemView.findViewById(R.id.list_item_task_actions);

      imageViewActions.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
      contextMenu.setHeaderTitle("Select an option");
      editTask = contextMenu.add("Edit");
      markAsDoneTask = contextMenu.add("Mark as done");
      editTask.setOnMenuItemClickListener(this);
      markAsDoneTask.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
      int position = getAdapterPosition();

      // Check for any errors
      if (listener == null || position == RecyclerView.NO_POSITION) {
        return false;
      }

      Task task = taskList.get(position);
      if (menuItem == editTask) {
        listener.onEditTaskSelected(task);
      } else if (menuItem == markAsDoneTask) {
        listener.onMarkTaskAsDoneSelected(task);
      }

      return false;
    }
  }

  public interface OnContextMenuItemSelectedListener {
    void onEditTaskSelected(Task task);
    void onMarkTaskAsDoneSelected(Task task);
  }

  public void setOnContextMenuItemSelectedListener(OnContextMenuItemSelectedListener listener) {
    this.listener = listener;
  }
}
