package com.example.exam_macia_first_term;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private String title;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private int imageId = 0;

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
            if (argsExercises != null) exercises = argsExercises;
            imageId = getArguments().getInt("image", 0);
        }

        TextView textTitle = view.findViewById(R.id.title);
        ImageView imageView = view.findViewById(R.id.detailImage);
        ListView listView = view.findViewById(R.id.listExercises);

        textTitle.setText(title);
        imageView.setImageResource(imageId);

        ExerciseAdapter adapter = new ExerciseAdapter(getContext(), exercises);
        listView.setAdapter(adapter);

        return view;
    }
}
