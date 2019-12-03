package com.gandh99.codeblocks.projectPage.tasks.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.authentication.AuthenticationInterceptor;
import com.gandh99.codeblocks.homePage.dashboard.viewModel.DashboardViewModel;
import com.gandh99.codeblocks.projectPage.tasks.AddTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.NewTaskActivity;
import com.gandh99.codeblocks.projectPage.tasks.TaskListAdapter;
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
  private static int DIALOG_REQUEST_ADD_CODE = 1;
  private RecyclerView recyclerView;
  private FloatingActionButton fab;
  private TaskViewModel taskViewModel;

  @Inject
  TaskListAdapter taskListAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskAPIService taskAPIService;

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

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(taskListAdapter);

    initFloatingActionButton();
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

//        AddTaskDialog dialog = new AddTaskDialog();
//        dialog.setTargetFragment(TasksFragment.this, DIALOG_REQUEST_ADD_CODE);
//        dialog.show(getActivity().getSupportFragmentManager(), "Create Task");
      }
    });
  }

  private void initViewModel() {
    taskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
    taskViewModel.getTasks().observe(this, new Observer<List<Task>>() {
      @Override
      public void onChanged(List<Task> tasks) {
        taskListAdapter.submitList(tasks);
      }
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

        taskAPIService.createTask(taskTitle, taskDescription, taskDateCreated, taskDeadline)
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
