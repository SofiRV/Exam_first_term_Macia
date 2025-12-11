package com.example.exam_macia_first_term;

import android.content.Intent;
import android.os.Bundle;
import android.view. LayoutInflater;
import android. view.View;
import android. widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android. widget.TextView;
import android.widget.Toast;

import androidx.appcompat. app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget. Toolbar;

import java.util.ArrayList;

public class AddTrainingActivity extends AppCompatActivity {

    private EditText editTrainingName;
    private RadioGroup radioGroupImages;
    private LinearLayout exercisesContainer;
    private ArrayList<Exercise> exercises;
    private int selectedImageId = R.drawable.legs_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        // Configurar toolbar
        Toolbar toolbar = findViewById(R.id. toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.add_training);
        }

        // Inicializar vistas
        editTrainingName = findViewById(R.id.editTrainingName);
        radioGroupImages = findViewById(R.id. radioGroupImages);
        exercisesContainer = findViewById(R.id.exercisesContainer);
        Button btnAddExercise = findViewById(R.id.btnAddExercise);
        Button btnSave = findViewById(R.id.btnSave);

        exercises = new ArrayList<>();

        // Listener para seleccionar imagen
        radioGroupImages.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioLegs) {
                selectedImageId = R.drawable.legs_icon;
            } else if (checkedId == R.id.radioArms) {
                selectedImageId = R.drawable.arms_icon;
            } else if (checkedId == R.id.radioBack) {
                selectedImageId = R.drawable.back_icon;
            } else if (checkedId == R.id.radioCardio) {
                selectedImageId = R.drawable.cardio_icon;
            }
        });

        // Botón para agregar ejercicio
        btnAddExercise.setOnClickListener(v -> showAddExerciseDialog());

        // Botón para guardar
        btnSave.setOnClickListener(v -> saveTraining());

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showAddExerciseDialog() {
        AlertDialog. Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater. from(this).inflate(R.layout.dialog_add_exercise, null);

        EditText editExerciseName = dialogView.findViewById(R.id. editExerciseName);
        EditText editDescription = dialogView.findViewById(R. id.editDescription);
        EditText editSets = dialogView.findViewById(R. id.editSets);
        EditText editReps = dialogView.findViewById(R.id.editReps);

        builder.setView(dialogView)
                .setTitle(R. string.add_exercise)
                .setPositiveButton(R. string.add_exercise, (dialog, which) -> {
                    String name = editExerciseName.getText().toString().trim();
                    String description = editDescription.getText().toString().trim();
                    String setsStr = editSets.getText().toString().trim();
                    String repsStr = editReps.getText().toString().trim();

                    if (name.isEmpty()) {
                        Toast.makeText(this, "Exercise name is required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int sets = setsStr.isEmpty() ? 0 : Integer.parseInt(setsStr);
                    int reps = repsStr.isEmpty() ? 0 : Integer.parseInt(repsStr);

                    if (description.isEmpty()) {
                        description = sets + "x" + reps;
                    }

                    Exercise exercise = new Exercise(name, description, sets, reps);
                    exercises.add(exercise);
                    addExerciseToView(exercise, exercises. size() - 1);
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    private void addExerciseToView(Exercise exercise, int position) {
        View exerciseView = LayoutInflater. from(this).inflate(R.layout.item_exercise_editable, exercisesContainer, false);

        TextView tvName = exerciseView.findViewById(R.id.tvExerciseName);
        TextView tvDetails = exerciseView.findViewById(R.id.tvExerciseDetails);
        Button btnRemove = exerciseView. findViewById(R.id.btnRemoveExercise);

        tvName.setText(exercise.getName());
        tvDetails.setText(exercise.getDescription() + " - " + exercise.getSets() + " sets x " + exercise.getRepsPerSet() + " reps");

        btnRemove.setOnClickListener(v -> {
            exercises.remove(position);
            exercisesContainer.removeView(exerciseView);
            refreshExercisesList();
        });

        exercisesContainer.addView(exerciseView);
    }

    private void refreshExercisesList() {
        exercisesContainer.removeAllViews();
        for (int i = 0; i < exercises.size(); i++) {
            addExerciseToView(exercises.get(i), i);
        }
    }

    private void saveTraining() {
        String trainingName = editTrainingName. getText().toString().trim();

        if (trainingName.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if (exercises.isEmpty()) {
            Toast.makeText(this, R.string.error_no_exercises, Toast.LENGTH_SHORT).show();
            return;
        }

        Training newTraining = new Training(trainingName, exercises, selectedImageId);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_training", newTraining);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}