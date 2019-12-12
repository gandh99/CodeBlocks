package com.gandh99.codeblocks.projectPage.tasks.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.projectPage.tasks.NewTaskActivity;
import com.gandh99.codeblocks.projectPage.tasks.SortTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.TaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSorter;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.api.TaskAPIService;
import com.gandh99.codeblocks.projectPage.tasks.viewModel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {
  private static int DIALOG_REQUEST_SORT_CODE = 1;
  private RecyclerView recyclerView;
  private FloatingActionButton fab;
  private Button buttonSort, buttonFilter;
  private TaskViewModel taskViewModel;
  private View dialogView;

  @Inject
  TaskAdapter taskAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskAPIService taskAPIService;

  @Inject
  TaskSorter taskSorter;

  public TasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_tasks, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_tasks);
    fab = view.findViewById(R.id.fab_tasks);
    buttonFilter = view.findViewById(R.id.button_filter);
    buttonSort = view.findViewById(R.id.button_sort);

    // Inflate the SortTaskDialog view so that we can sort the task list later
    dialogView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_sort_task, null);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(taskAdapter);
    taskAdapter.setPriorityTypes(getResources());

    initFloatingActionButton();
    initSortButton();
    initViewModel();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initFloatingActionButton() {
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getContext(), NewTaskActivity.class);
        startActivityForResult(intent, NewTaskActivity.NEW_TASK_REQUEST_CODE);
      }
    });
  }

  private void initSortButton() {
    buttonSort.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SortTaskDialog dialog = new SortTaskDialog();
        dialog.setTargetFragment(TasksFragment.this, DIALOG_REQUEST_SORT_CODE);
        dialog.show(getActivity().getSupportFragmentManager(), "Sort Task");
      }
    });
  }

  private void initViewModel() {
    taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
    taskViewModel.getTasks().observe(this, tasks -> {
      List<Task> sortedTaskList = taskSorter.sortTasks(TasksFragment.this.getContext(), dialogView, tasks);
      taskAdapter.updateList(sortedTaskList);
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == NewTaskActivity.NEW_TASK_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        String taskTitle = data.getStringExtra(NewTaskActivity.INTENT_TASK_TITLE);
        String taskDescription = data.getStringExtra(NewTaskActivity.INTENT_TASK_DESCRIPTION);
        String taskDateCreated = getCurrentDate();
        String taskDeadline = data.getStringExtra(NewTaskActivity.INTENT_TASK_DEADLINE);
        String taskPriority = data.getStringExtra(NewTaskActivity.INTENT_TASK_PRIORITY);

        taskAPIService.createTask(taskTitle, taskDescription, taskDateCreated, taskDeadline, taskPriority)
          .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if (response.isSuccessful()) {
                Toast.makeText(getContext(), "Successfully created task", Toast.LENGTH_SHORT).show();
                refreshTaskList();
              }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
          });
      }
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private String getCurrentDate() {
    LocalDate localDate = LocalDate.now();
    return localDate.getYear() + "-" + localDate.getMonth().getValue() + "-" + localDate.getDayOfMonth();
  }

  private void refreshTaskList() {
    taskViewModel.refreshTaskList();
  }

}
