package com.example.exam_macia_first_term;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private String description;
    private int reps;
    private int duration;

    public Exercise(String name, String description, int reps, int duration){
        this.name = name;
        this.description = description;
        this.reps = reps;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReps() {
        return reps;
    }

    public int getDuration() {
        return duration;
    }
}
