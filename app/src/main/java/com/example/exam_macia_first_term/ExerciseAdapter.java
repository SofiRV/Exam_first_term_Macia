package com.example.exam_macia_first_term;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    public ExerciseAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_exercise, parent, false);
        }

        Exercise exercise = getItem(position);

        TextView name = convertView.findViewById(R.id.exerciseName);
        TextView desc = convertView.findViewById(R.id.exerciseDesc);
        TextView series = convertView.findViewById(R.id.exerciseSeries);
        TextView reps = convertView.findViewById(R.id.exerciseReps);

        name.setText(exercise.getName());
        desc.setText(exercise.getDescription());
        reps.setText("Reps: " + exercise.getReps());
        series.setText("Series: " + exercise.getDuration());


        return convertView;
    }
}
