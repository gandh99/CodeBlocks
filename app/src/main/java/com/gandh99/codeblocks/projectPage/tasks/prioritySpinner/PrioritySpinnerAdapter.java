package com.gandh99.codeblocks.projectPage.tasks.prioritySpinner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.gandh99.codeblocks.R;

public class PrioritySpinnerAdapter extends ArrayAdapter {
  private Context context;
  private int[] priorityColour;
  private String[] priority;

  public PrioritySpinnerAdapter(@NonNull Context context) {
    super(context, R.layout.spinner_priority);
    this.context = context;
    priority = context.getResources().getStringArray(R.array.priority);
    priorityColour = new int[] {
      ResourcesCompat.getColor(context.getResources(), R.color.colorWhite, null),
      ResourcesCompat.getColor(context.getResources(), R.color.colorGreen, null),
      ResourcesCompat.getColor(context.getResources(), R.color.colorOrange, null),
      ResourcesCompat.getColor(context.getResources(), R.color.colorRed, null)
    };
  }

  @Override
  public int getCount() {
    return priority.length;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    PrioritySpinnerViewHolder viewHolder = new PrioritySpinnerViewHolder();

    if (convertView == null) {
      LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.spinner_priority, parent, false);
      viewHolder.imageViewColour = convertView.findViewById(R.id.imageView_priority);
      viewHolder.textViewPriority = convertView.findViewById(R.id.textView_priority);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (PrioritySpinnerViewHolder) convertView.getTag();
    }

    viewHolder.imageViewColour.setBackgroundColor(priorityColour[position]);
    viewHolder.textViewPriority.setText(priority[position]);

    return convertView;
  }

  @Override
  public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    return getView(position, convertView, parent);
  }

  class PrioritySpinnerViewHolder {
    ImageView imageViewColour;
    TextView textViewPriority;
  }
}
