package com.example.exam_macia_first_term;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isDualPane;
    private ArrayList<Training> allTrainings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allTrainings = getTrainingData();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isDualPane = findViewById(R.id.detailContainer) != null;
        FragmentManager fm = getSupportFragmentManager();

        ListFragment listFragment = (ListFragment) fm.findFragmentByTag("listFragment");
        int targetContainerId = isDualPane ? R.id.listContainer : R.id.main;

        if (listFragment == null) {
            listFragment = new ListFragment();
            fm.beginTransaction()
                    .add(targetContainerId, listFragment, "listFragment")
                    .commit();
        }

        // Si hay selección previa en dual pane
        if (isDualPane && !allTrainings.isEmpty()) {
            showTrainingDetails(allTrainings.get(0));
        }
    }

    public ArrayList<Training> getAllTrainings() {
        return allTrainings;
    }

    public boolean isDualPane() {
        return isDualPane;
    }

    public void onTrainingSelected(int position) {
        showTrainingDetails(allTrainings.get(position));
    }

    private void showTrainingDetails(Training training) {
        DetailFragment detailFragment = DetailFragment.newInstance(
                training.getTitle(),
                training.getExercises(),
                training.getImageId()
        );

        if (isDualPane()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer, detailFragment, "detailFragment")
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, detailFragment, "detailFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

    // -------------------

// Datos de ejemplo

// -------------------

    private ArrayList<Training> getTrainingData() {



        ArrayList<Exercise> legExercises = new ArrayList<>();

        legExercises.add(new Exercise("Squats", "4x10", 4, 10));

        legExercises.add(new Exercise("Lunges", "4x10", 4, 10));

        legExercises.add(new Exercise("Calf Raises", "4x10", 4, 10));



        ArrayList<Exercise> armExercises = new ArrayList<>();

        armExercises.add(new Exercise("Bench Press", "4x10", 4, 10));

        armExercises.add(new Exercise("Incline Press", "4x10", 4, 10));

        armExercises.add(new Exercise("Decline Press", "4x10", 4, 10));



        ArrayList<Exercise> backExercises = new ArrayList<>();

        backExercises.add(new Exercise("Lat Pulldown", "4x10", 4, 10));

        backExercises.add(new Exercise("Pull Ups", "4x10", 4, 10));

        backExercises.add(new Exercise("Deadlift", "4x10", 4, 10));



        ArrayList<Exercise> cardioExercises = new ArrayList<>();

        cardioExercises.add(new Exercise("Running", "10km", 0, 10));

        cardioExercises.add(new Exercise("Cycling", "10km", 0, 10));

        cardioExercises.add(new Exercise("Swimming", "10km", 0, 10));



        ArrayList<Training> trainings = new ArrayList<>();

        trainings.add(new Training("Legs", legExercises, R.drawable.legs_icon));

        trainings.add(new Training("Arms", armExercises, R.drawable.arms_icon));

        trainings.add(new Training("Back", backExercises, R.drawable.back_icon));

        trainings.add(new Training("Cardio", cardioExercises, R.drawable.cardio_icon));



        return trainings;

    }

}
