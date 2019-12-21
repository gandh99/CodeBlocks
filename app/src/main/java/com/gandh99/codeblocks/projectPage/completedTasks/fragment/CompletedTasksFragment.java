package com.gandh99.codeblocks.projectPage.completedTasks.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.Refreshable;
import com.gandh99.codeblocks.projectPage.completedTasks.CompletedTaskAdapter;
import com.gandh99.codeblocks.projectPage.completedTasks.viewModel.CompletedTaskViewModel;
import com.gandh99.codeblocks.projectPage.tasks.dialog.FilterTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.dialog.SortTaskDialog;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.gandh99.codeblocks.projectPage.tasks.fragment.TasksFragment;
import com.gandh99.codeblocks.projectPage.tasks.taskSorter.TaskSorter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedTasksFragment extends Fragment implements Refreshable {
  private static int DIALOG_REQUEST_SORT_CODE = 1;
  private static final int DIALOG_REQUEST_FILTER_CODE = 2;
  private RecyclerView recyclerView;
  private Button buttonSort, buttonFilter;
  private CompletedTaskViewModel completedTaskViewModel;
  private View sortTaskDialogView, filterTaskDialogView;

  @Inject
  CompletedTaskAdapter completedTaskAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @Inject
  TaskSorter taskSorter;

  public CompletedTasksFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Configure Dagger
    configureDagger();

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_completed_tasks, container, false);
    recyclerView = view.findViewById(R.id.recyclerView_tasks);
    buttonFilter = view.findViewById(R.id.button_filter);
    buttonSort = view.findViewById(R.id.button_sort);

    // Inflate the SortTaskDialog view so that we can sort the task list later
    sortTaskDialogView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_sort_task, null);

    // Inflate the FilterTaskDialog view so that we can filter the task list later
    filterTaskDialogView =
      LayoutInflater
        .from(getContext())
        .inflate(R.layout.dialog_filter_task, null);

    // Setup recyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(completedTaskAdapter);
    completedTaskAdapter.setContext(getContext());
    completedTaskAdapter.setPriorityTypes(getResources());

    initFilterButton();
    initSortButton();
    initViewModel();

    return view;
  }

  private void configureDagger() {
    AndroidSupportInjection.inject(this);
  }

  private void initFilterButton() {
    buttonFilter.setOnClickListener(view -> {
      FilterTaskDialog dialog = new FilterTaskDialog();
      dialog.setTargetFragment(CompletedTasksFragment.this, DIALOG_REQUEST_FILTER_CODE);
      dialog.setGenericTaskAdapter(completedTaskAdapter);
      dialog.show(getActivity().getSupportFragmentManager(), "Filter Task");
    });
  }

  private void initSortButton() {
    buttonSort.setOnClickListener(view -> {
      SortTaskDialog dialog = new SortTaskDialog();
      dialog.setTargetFragment(CompletedTasksFragment.this, DIALOG_REQUEST_SORT_CODE);
      dialog.setGenericTaskAdapter(completedTaskAdapter);
      dialog.show(getActivity().getSupportFragmentManager(), "Sort Task");
    });
  }

  private void initViewModel() {
    completedTaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(CompletedTaskViewModel.class);
    completedTaskViewModel.getTasks().observe(this, tasks -> {
      List<Task> displayTaskList = tasks;

      // Filter and sort the task list
      displayTaskList = taskSorter.sortTasks(CompletedTasksFragment.this.getContext(),
        sortTaskDialogView, displayTaskList);
      displayTaskList = taskSorter.sortTasks(CompletedTasksFragment.this.getContext(), filterTaskDialogView, displayTaskList);

      // Provide the adapter with the list of all tasks, as well as the display list
      completedTaskAdapter.updateListOfAllTasks(tasks);
      completedTaskAdapter.updateDisplayTaskList(displayTaskList);
    });
  }

  public void refresh() {
    completedTaskViewModel.refreshTaskList();
  }
}
