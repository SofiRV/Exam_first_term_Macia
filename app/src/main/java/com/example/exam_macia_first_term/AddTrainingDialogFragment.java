package com.example.exam_macia_first_term;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AddTrainingDialogFragment extends DialogFragment {

    public interface AddTrainingListener {
        void onTrainingAdded(Training newTraining);
    }

    private EditText editTitle;
    private LinearLayout exercisesContainer;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_training, null);

        editTitle = view.findViewById(R.id.newTrainingTitle);
        exercisesContainer = view.findViewById(R.id.newexercisesContainer);
        Button btnAddExercise = view.findViewById(R.id.btnAddExercise);

        btnAddExercise.setOnClickListener(v -> addExerciseField());

        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Añadir entrenamiento")
                .setView(view)
                .setPositiveButton("Añadir", null) // Listener lo pondremos después
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String title = editTitle.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(getContext(), "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Exercise> exercises = new ArrayList<>();
                for (int i = 0; i < exercisesContainer.getChildCount(); i++) {
                    View exerciseView = exercisesContainer.getChildAt(i);
                    EditText etName = exerciseView.findViewById(R.id.editNewExerciseName);
                    EditText etSeries = exerciseView.findViewById(R.id.editNewExerciseSeries);
                    EditText etReps = exerciseView.findViewById(R.id.editNewExerciseReps);

                    String name = etName.getText().toString().trim();
                    int series = parseInt(etSeries.getText().toString().trim());
                    int reps = parseInt(etReps.getText().toString().trim());

                    if (!TextUtils.isEmpty(name)) {
                        exercises.add(new Exercise(name, series + "x" + reps, series, reps));
                    }
                }

                Training newTraining = new Training(title, exercises, R.drawable.legs_icon);

                if (getActivity() instanceof AddTrainingListener) {
                    ((AddTrainingListener) getActivity()).onTrainingAdded(newTraining);
                }

                dialog.dismiss(); // Cerramos manualmente solo si todo está OK
            });
        });

        return dialog;
    }


    // Añade un nuevo bloque de ejercicio al contenedor
    private void addExerciseField() {
        View exerciseView = LayoutInflater.from(getContext()).inflate(R.layout.item_exercise_input, exercisesContainer, false);
        exercisesContainer.addView(exerciseView);
    }

    // Convierte texto a entero, retorna 0 si no es válido
    private int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
