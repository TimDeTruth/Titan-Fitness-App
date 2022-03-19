package com.bcit.titan;

import java.io.Serializable;

public class ExerciseDetails implements Serializable {


    private String nameOfExercise;
    private int resourceId;
    private int id;

    public ExerciseDetails(String nameOfExercise, int resourceId, int id) {
        this.nameOfExercise = nameOfExercise;
        this.resourceId = resourceId;
        this.id = id;
    }

    public String getNameOfExercise() {
        return nameOfExercise;
    }

    public void setNameOfExercise(String nameOfExercise) {
        this.nameOfExercise = nameOfExercise;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return this.resourceId;
    }

}
