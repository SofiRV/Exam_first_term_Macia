package com.example.exam_macia_first_term;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Training> trainings = new ArrayList<>();
    private int selectedPosition = -1; // Posición del entrenamiento seleccionado

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listViewEntrenamientos);

        trainings = createTraining();

        TrainingAdapter adapter = new TrainingAdapter(getContext(), trainings);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedPosition = position; // Guardamos la posición seleccionada
            showDetailFragment(position);
        });

        return view;
    }

    private void showDetailFragment(int position) {
        Training selectedTraining = trainings.get(position);
        Fragment detailsFragment = DetailFragment.newInstance(
                selectedTraining.getTitle(),
                selectedTraining.getExercises(),
                selectedTraining.getImageId()
        );

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            if (activity.isDualPane()) {
                // Landscape → colocar en detailContainer
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailContainer, detailsFragment, "detailFragment")
                        .commit();
            } else {
                // Portrait → reemplazar main
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, detailsFragment, "detailFragment")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedPosition", selectedPosition);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            selectedPosition = savedInstanceState.getInt("selectedPosition", -1);
            if (selectedPosition != -1) {
                // Restaurar fragment de detalle en landscape
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null && activity.isDualPane()) {
                    showDetailFragment(selectedPosition);
                }
            }
        }
    }

    private ArrayList<Training> createTraining() {
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
