package com.bcit.titan;

import java.util.ArrayList;

public class Exercise {

/*
    private String[][] exerciseDone; // pullups upperbody,

    private String[] upperBody = {"push ups" , "pull ups ", "dips"};
    private int[] upperbodyreps = {10 ,0, 3};
*/



    private String username;
    private ArrayList<PartAndExercise> partAndExercise = new ArrayList<>();

    public Exercise(String username, ArrayList<PartAndExercise> partAndExercise) {
        this.username = username;
        this.partAndExercise = partAndExercise;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<PartAndExercise> getPartAndExercise() {
        return partAndExercise;
    }

    public void setPartAndExercise(ArrayList<PartAndExercise> partAndExercise) {
        this.partAndExercise = partAndExercise;
    }
}
