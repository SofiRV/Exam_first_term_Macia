package com.example.exam_macia_first_term;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Training> trainings;  // ✅ NO inicializar aquí
    private int selectedPosition = -1;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R. id.listViewEntrenamientos);

        // ✅ OBTENER DATOS DE LA ACTIVITY (fuente única de verdad)
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            trainings = activity.getTrainings();
        }

        // ✅ Fallback si activity es null
        if (trainings == null) {
            trainings = new ArrayList<>();
        }

        TrainingAdapter adapter = new TrainingAdapter(requireContext(), trainings);  // ✅ requireContext()
        listView.setAdapter(adapter);

        // ✅ DELEGAR A LA ACTIVITY (en vez de gestionar fragmentos aquí)
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedPosition = position;

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.onTrainingSelected(position);
            }
        });

        return view;
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
            // ✅ Ya NO restauramos el detalle aquí (MainActivity lo hace)
        }
    }

    // ✅ MÉTODO createTraining() ELIMINADO COMPLETAMENTE
    // Al final de ListFragment.java, ANTES del último }

    public void updateTrainings(ArrayList<Training> newTrainings) {
        Log.d("ListFragment", "===== updateTrainings CALLED =====");
        Log.d("ListFragment", "New trainings size: " + (newTrainings != null ? newTrainings.size() : "NULL"));

        if (newTrainings == null || newTrainings.isEmpty()) {
            Log.d("ListFragment", "newTrainings is NULL or empty, returning");
            return;
        }

        // ✅ Actualizar la referencia
        this.trainings = newTrainings;

        if (listView == null) {
            Log.e("ListFragment", "ERROR: listView is NULL!");
            return;
        }

        if (getContext() == null) {
            Log.e("ListFragment", "ERROR:  getContext() is NULL!");
            return;
        }

        // ✅ SIEMPRE CREAR UN ADAPTER NUEVO (más confiable)
        Log.d("ListFragment", "Creating NEW adapter with " + newTrainings.size() + " trainings");

        TrainingAdapter newAdapter = new TrainingAdapter(requireContext(), newTrainings);
        listView.setAdapter(newAdapter);

        // Reconfigurar el click listener
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedPosition = position;
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                mainActivity.onTrainingSelected(position);
            }
        });

        Log.d("ListFragment", "Adapter set with " + newAdapter.getCount() + " items");
        Log.d("ListFragment", "===== updateTrainings FINISHED =====");
    }
}