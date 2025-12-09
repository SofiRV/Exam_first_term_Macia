package com.example.exam_macia_first_term;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget. LinearLayout;
import android.widget. TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private String title;
    private ArrayList<Exercise> exercises;
    private int imageId;

    public DetailFragment() {}

    public static DetailFragment newInstance(String title, ArrayList<Exercise> exercises, int imageId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("exercises", exercises);
        args.putInt("image", imageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        if (getArguments() != null) {
            title = getArguments().getString("title", "");
            ArrayList<Exercise> argsExercises = (ArrayList<Exercise>) getArguments().getSerializable("exercises");
            if (argsExercises != null) {
                exercises = argsExercises;
            } else {
                exercises = new ArrayList<>();
            }
            imageId = getArguments().getInt("image", 0);
        }

        TextView textTitle = view.findViewById(R.id.title);
        ImageView imageView = view.findViewById(R. id.detailImage);
        LinearLayout exercisesContainer = view. findViewById(R.id.exercisesContainer);

        // Validaciones
        if (textTitle != null) {
            textTitle. setText(title != null ? title : "Unknown");
        }

        if (imageView != null && imageId != 0) {
            imageView.setImageResource(imageId);
        }

        // ✅ AGREGAR EJERCICIOS DINÁMICAMENTE (en vez de ListView)
        if (exercisesContainer != null && exercises != null && ! exercises.isEmpty()) {
            for (Exercise exercise : exercises) {
                View exerciseView = inflater.inflate(R.layout.item_exercise, exercisesContainer, false);

                TextView name = exerciseView.findViewById(R.id.exerciseName);
                TextView desc = exerciseView.findViewById(R.id.exerciseDesc);
                TextView series = exerciseView.findViewById(R.id.exerciseSeries);
                TextView reps = exerciseView.findViewById(R.id.exerciseReps);

                if (exercise != null) {
                    String exerciseName = exercise.getName();
                    name.setText(exerciseName != null ? exerciseName : "Unknown");

                    String exerciseDesc = exercise.getDescription();
                    desc.setText(exerciseDesc != null ? exerciseDesc : "");

                    series.setText("Series:  " + exercise.getSets());
                    reps.setText("Reps: " + exercise.getRepsPerSet());
                }

                exercisesContainer.addView(exerciseView);
            }
        }

        return view;
    }
}