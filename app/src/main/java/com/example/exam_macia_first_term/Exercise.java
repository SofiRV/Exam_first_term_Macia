package com.example.exam_macia_first_term;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private String description;
    private int sets;        // ✅ RENOMBRADO de 'reps'
    private int repsPerSet;  // ✅ RENOMBRADO de 'duration'

    public Exercise(String name, String description, int sets, int repsPerSet) {
        // ✅ VALIDACIONES AGREGADAS
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be empty");
        }
        if (sets < 0 || repsPerSet < 0) {
            throw new IllegalArgumentException("Sets and reps cannot be negative");
        }

        this.name = name;
        this.description = description != null ? description : "";
        this.sets = sets;
        this.repsPerSet = repsPerSet;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // ✅ NUEVOS GETTERS CON NOMBRES CORRECTOS
    public int getSets() {
        return sets;
    }

    public int getRepsPerSet() {
        return repsPerSet;
    }

    // ✅ DEPRECATED - Mantener temporalmente para compatibilidad
    @Deprecated
    public int getReps() {
        return sets;
    }

    @Deprecated
    public int getDuration() {
        return repsPerSet;
    }
}