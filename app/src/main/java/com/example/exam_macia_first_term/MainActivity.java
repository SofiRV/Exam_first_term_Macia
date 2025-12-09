package com.example.exam_macia_first_term;

import android.os. Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isDualPane;
    private static final String KEY_SELECTED_ID = "selected_training_id";
    private int currentSelectedTrainingId = -1;

    private ArrayList<Training> allTrainings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialización de datos
        this.allTrainings = getTrainingData();

        setContentView(R.layout.activity_main);

        // Configuración de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar. setTitle(R.string.training);  // ✅ Usando string resource
        }

        isDualPane = findViewById(R.id.detailContainer) != null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Restaurar el ID guardado
        if (savedInstanceState != null) {
            currentSelectedTrainingId = savedInstanceState. getInt(KEY_SELECTED_ID, -1);
        }

        // Gestión del ListFragment
        ListFragment listFragment = (ListFragment) fragmentManager.findFragmentByTag("listFragment");

        int targetContainerId = isDualPane ? R.id.listContainer : R.id.main;

        if (listFragment == null) {
            listFragment = new ListFragment();

            fragmentManager.beginTransaction()
                    .add(targetContainerId, listFragment, "listFragment")
                    .commit();  // ✅ Cambiado a commit normal
        } else {
            boolean needsReallocation = listFragment.getId() != targetContainerId;

            if (needsReallocation) {
                fragmentManager.beginTransaction()
                        .remove(listFragment)
                        . commitNow();

                ListFragment newListFragment = new ListFragment();

                fragmentManager.beginTransaction()
                        .replace(targetContainerId, newListFragment, "listFragment")
                        .commit();  // ✅ Cambiado a commit normal
            }
        }

        // Gestión del DetailFragment (Restauración)
        if (currentSelectedTrainingId != -1) {
            if (currentSelectedTrainingId < allTrainings.size()) {
                Training selected = allTrainings.get(currentSelectedTrainingId);

                if (! isDualPane) {
                    ListFragment existingListFragment = (ListFragment) fragmentManager.findFragmentByTag("listFragment");
                    if (existingListFragment != null) {
                        fragmentManager.beginTransaction()
                                .remove(existingListFragment)
                                .commitNow();
                    }
                }

                DetailFragment detailFragment = (DetailFragment) fragmentManager.findFragmentByTag("detailFragment");

                if (detailFragment == null || isDualPane) {
                    showTrainingDetails(selected);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_ID, currentSelectedTrainingId);
    }

    public boolean isDualPane() {
        return isDualPane;
    }

    // ✅ NUEVO GETTER PARA COMPARTIR DATOS
    public ArrayList<Training> getTrainings() {
        return allTrainings;
    }

    // ✅ ESTE MÉTODO AHORA SE USA (antes estaba huérfano)
    public void onTrainingSelected(int position) {
        this.currentSelectedTrainingId = position;

        if (position >= 0 && position < allTrainings.size()) {
            Training selected = allTrainings.get(position);
            showTrainingDetails(selected);
        }
    }

    private void showTrainingDetails(Training selected) {
        if (selected == null) return;  // ✅ Validación agregada

        DetailFragment detailsFragment = DetailFragment.newInstance(
                selected.getTitle(),
                selected.getExercises(),
                selected.getImageId()
        );

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isDualPane) {
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, detailsFragment, "detailFragment")
                    .commit();  // ✅ Cambiado a commit normal
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main, detailsFragment, "detailFragment")
                    .addToBackStack(null)
                    .commit();  // ✅ Cambiado a commit normal
        }
    }

    private ArrayList<Training> getTrainingData() {
        // ✅ PARÁMETROS ACTUALIZADOS (sets, repsPerSet)
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
        trainings.add(new Training("Legs", legExercises, R. drawable.legs_icon));
        trainings.add(new Training("Arms", armExercises, R.drawable.arms_icon));
        trainings.add(new Training("Back", backExercises, R. drawable.back_icon));
        trainings.add(new Training("Cardio", cardioExercises, R.drawable.cardio_icon));

        return trainings;
    }
}