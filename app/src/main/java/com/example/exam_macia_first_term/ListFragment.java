package com.example.exam_macia_first_term;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Training> trainings = new ArrayList<>();
    private MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
            trainings = activity.getAllTrainings();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listTrainings);

        // Crear un ArrayAdapter con los títulos de los entrenamientos
        ArrayList<String> titles = new ArrayList<>();
        for (Training t : trainings) {
            titles.add(t.getTitle());
        }

        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (activity != null) {
                activity.onTrainingSelected(position);
            }
        });

        return view;
    }

    // Método para actualizar la lista si cambian los entrenamientos
    public void updateTrainings(ArrayList<Training> newTrainings) {
        trainings.clear();
        trainings.addAll(newTrainings);

        // Actualizar títulos en el adapter
        ArrayList<String> titles = new ArrayList<>();
        for (Training t : trainings) {
            titles.add(t.getTitle());
        }
        adapter.clear();
        adapter.addAll(titles);
        adapter.notifyDataSetChanged();
    }
}
