package com.bcit.titan;

public class PartAndExercise {

    private String partOfBody;
    private String typeOfWorkout;
    private int reps;

    public PartAndExercise(String partOfBody, String typeOfWorkout, int reps) {
        this.partOfBody = partOfBody;
        this.typeOfWorkout = typeOfWorkout;
        this.reps = reps;
    }

    public String getPartOfBody() {
        return partOfBody;
    }

    public void setPartOfBody(String partOfBody) {
        this.partOfBody = partOfBody;
    }

    public String getTypeOfWorkout() {
        return typeOfWorkout;
    }

    public void setTypeOfWorkout(String typeOfWorkout) {
        this.typeOfWorkout = typeOfWorkout;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
