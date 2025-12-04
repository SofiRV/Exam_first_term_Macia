package com.example.exam_macia_first_term;

import java.io.Serializable;
import java.util.ArrayList;

public class Training implements Serializable {
    private String title;
    private ArrayList<Exercise> exercises;
    private int imageId;

    public Training(String title, ArrayList<Exercise> exercises, int imageId) {
        this.title = title;
        this.exercises = exercises;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public int getImageId() {
        return imageId;
    }

    public void addExercise(Exercise exercise) {
        if(exercises == null)
            exercises = new ArrayList<Exercise>();
        exercises.add(exercise);
    }
}
