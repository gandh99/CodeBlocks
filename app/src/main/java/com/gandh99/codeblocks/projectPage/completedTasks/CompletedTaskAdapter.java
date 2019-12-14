package com.gandh99.codeblocks.projectPage.completedTasks;

import android.content.res.Resources;
import android.nfc.FormatException;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gandh99.codeblocks.R;
import com.gandh99.codeblocks.common.dateFormatting.CustomDateFormatter;
import com.gandh99.codeblocks.common.dateFormatting.DatePortion;
import com.gandh99.codeblocks.projectPage.GenericTaskAdapter;
import com.gandh99.codeblocks.projectPage.tasks.api.Task;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompletedTaskAdapter extends GenericTaskAdapter {
}
