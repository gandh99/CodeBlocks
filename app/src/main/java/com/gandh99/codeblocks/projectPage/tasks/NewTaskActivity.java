package com.gandh99.codeblocks.projectPage.tasks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.gandh99.codeblocks.R;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  private DatePickerDialog datePickerDialog;
  private ImageView buttonDatePicker;
  private EditText editTextDeadline;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_task);
    getSupportActionBar().setTitle("Create New Task");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    editTextDeadline = findViewById(R.id.editText_task_deadline);
    buttonDatePicker = findViewById(R.id.button_date_picker);
    buttonDatePicker.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onClick(View view) {
        datePickerDialog = new DatePickerDialog(NewTaskActivity.this);
        datePickerDialog.setOnDateSetListener(NewTaskActivity.this);
        datePickerDialog.show();
      }
    });
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  @Override
  public void onDateSet(DatePicker datePicker, int year, int month, int day) {
    String date = year + "-" + (month + 1) + "-" + day;
    editTextDeadline.setText(date);
  }
}
