package com.example.exam_macia_first_term;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainingAdapter extends ArrayAdapter<Training> {

    public TrainingAdapter(Context context, ArrayList<Training> trainings) {
        super(context, 0, trainings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_training, parent, false);
        }

        Training training = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageTraining);
        TextView textView = convertView.findViewById(R.id.textTraining);

        if (training != null) {
            textView.setText(training.getTitle());
            imageView.setImageResource(training.getImageId());
        }

        return convertView;
    }
}
