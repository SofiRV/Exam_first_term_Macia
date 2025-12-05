package com.example.exam_macia_first_term;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isDualPane;
    private static final String KEY_SELECTED_ID = "selected_training_id";
    private int currentSelectedTrainingId = -1; // -1 = Ninguno seleccionado

    // La lista de datos debe estar accesible por la Activity
    private ArrayList<Training> allTrainings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inicialización de datos
        this.allTrainings = getTrainingData();

        setContentView(R.layout.activity_main);

        // Configuración de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Trainings");
        }

        isDualPane = findViewById(R.id.detailContainer) != null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 2. Restaurar el ID guardado.
        if (savedInstanceState != null) {
            currentSelectedTrainingId = savedInstanceState.getInt(KEY_SELECTED_ID, -1);
        }

        // 3. Gestión del ListFragment (CORRECCIÓN FINAL: Asegurar el contenedor correcto)
        ListFragment listFragment = (ListFragment) fragmentManager.findFragmentByTag("listFragment");

        int targetContainerId = isDualPane ? R.id.listContainer : R.id.main;

        if (listFragment == null) {
            // Caso A: Primera ejecución. Crear y añadir.
            listFragment = new ListFragment();

            fragmentManager.beginTransaction()
                    .add(targetContainerId, listFragment, "listFragment")
                    .commitAllowingStateLoss();
        } else {
            // Caso B: El fragmento existe (restauración por rotación).

            // Usamos una nueva instancia solo si el Fragmento Restaurado
            // está en un contenedor diferente al actual (rotación).
            boolean needsReallocation = listFragment.getId() != targetContainerId;

            if (needsReallocation) {
                // El fragmento restaurado está en el contenedor de Portrait/Landscape y ahora
                // necesitamos moverlo al otro contenedor.

                // 1. Remover el antiguo.
                fragmentManager.beginTransaction()
                        .remove(listFragment)
                        .commitNowAllowingStateLoss();

                // 2. Crear una nueva instancia limpia y colocarla en el contenedor actual (Landscape/Portrait).
                ListFragment newListFragment = new ListFragment();

                fragmentManager.beginTransaction()
                        .replace(targetContainerId, newListFragment, "listFragment")
                        .commitAllowingStateLoss();
            }
            // Si no necesita reubicación, se deja como está (el Fragment Manager ya lo restauró correctamente).
        }


        // 4. Gestión del DetailFragment (Restauración de la vista de detalle)
        if (currentSelectedTrainingId != -1) {

            if (currentSelectedTrainingId < allTrainings.size()) {
                Training selected = allTrainings.get(currentSelectedTrainingId);

                // CORRECCIÓN CRÍTICA PARA ROTACIÓN A PORTRAIT:
                if (!isDualPane) {
                    // Si estamos en Portrait (single-pane) y había un detalle seleccionado,
                    // eliminamos cualquier listFragment que Android haya restaurado en R.id.main
                    // para que el DetailFragment ocupe su lugar.
                    ListFragment existingListFragment = (ListFragment) fragmentManager.findFragmentByTag("listFragment");
                    if (existingListFragment != null) {
                        fragmentManager.beginTransaction()
                                .remove(existingListFragment)
                                .commitNowAllowingStateLoss();
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
        // Guarda el ID del elemento que estaba seleccionado
        outState.putInt(KEY_SELECTED_ID, currentSelectedTrainingId);
    }

    public boolean isDualPane() {
        return isDualPane;
    }

    // Método llamado por el ListFragment al hacer click.
    public void onTrainingSelected(int position) {
        this.currentSelectedTrainingId = position;

        // Obtenemos el objeto Training basado en el ID/posición.
        Training selected = allTrainings.get(position);

        showTrainingDetails(selected);
    }

    // Método central para gestionar las transacciones del DetailFragment
    private void showTrainingDetails(Training selected) {
        DetailFragment detailsFragment = DetailFragment.newInstance(
                selected.getTitle(),
                selected.getExercises(),
                selected.getImageId()
        );

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isDualPane) {
            // Landscape: Reemplaza detailContainer
            fragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, detailsFragment, "detailFragment")
                    .commitAllowingStateLoss(); // ✅ Corregido el crash por rotación
        } else {
            // Portrait: Reemplaza main y añade a la pila
            fragmentManager.beginTransaction()
                    .replace(R.id.main, detailsFragment, "detailFragment")
                    .addToBackStack(null) // Esto permite volver a la lista con el botón 'atrás'
                    .commitAllowingStateLoss(); // ✅ Corregido el crash por rotación
        }
    }

    // Método que proporciona los datos de entrenamiento
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
        // Asegúrate de que los IDs de tus recursos (R.drawable) sean correctos
        trainings.add(new Training("Legs", legExercises, R.drawable.legs_icon));
        trainings.add(new Training("Arms", armExercises, R.drawable.arms_icon));
        trainings.add(new Training("Back", backExercises, R.drawable.back_icon));
        trainings.add(new Training("Cardio", cardioExercises, R.drawable.cardio_icon));

        return trainings;
    }
}