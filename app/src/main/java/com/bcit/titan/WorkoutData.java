package com.bcit.titan;

public class WorkoutData {
    private static String exercise_type;
    private static int image;
    private static int workoutTime;
    private static String exerciseName;
    private static float workout_level = 1f;


    public void set_workout(String exerciseName) {
        switch (exerciseName) {
            //upper
            case "Push Ups":
                set_push_up();
                break;
            case "Dips":
                set_dips();
                break;
            case "Pull Ups":
                set_pull_ups();
                break;
            //Lower
            case "Squats":
                set_squat();
                break;
            case "Lunges":
                set_lunges();
                break;
            case "Deadlift":
                set_deadlift();
                break;
            //Core
            case "Plank":
                set_plank();
                break;
            case "Leg Raises":
                set_leg_raises();
                break;
            case "Elbow to knee":
                set_elbow_to_knee();
                break;
        }
    }

    // Upper
    public void set_push_up() {
        exercise_type = "upper";
        image = R.drawable.exercise_upper_push_ups;
        workoutTime = 30000;
        exerciseName = "Push up";
    }

    public void set_dips() {
        exercise_type = "upper";
        image = R.drawable.exercise_upper_dips;
        workoutTime = 45000;
        exerciseName = "Dips";
    }

    public void set_pull_ups() {
        exercise_type = "upper";
        image = R.drawable.exercise_upper_pull_ups;
        workoutTime = 50000;
        exerciseName = "Pull ups";
    }

    // Lower
    public void set_squat() {
        exercise_type = "lower";
        image = R.drawable.exercise_lower_squats;
        workoutTime = 60000;
        exerciseName = "Squats";
    }

    public void set_lunges() {
        exercise_type = "lower";
        image = R.drawable.exercise_lower_lunge;
        workoutTime = 60000;
        exerciseName = "Lunges";
    }

    public void set_deadlift() {
        exercise_type = "lower";
        image = R.drawable.exercise_lower_deadlift;
        workoutTime = 45000;
        exerciseName = "Deadlift";
    }

    // Core
    public void set_plank() {
        exercise_type = "core";
        image = R.drawable.exercise_core_plank;
        workoutTime = 120000;
        exerciseName = "Plank";
    }
    public void set_leg_raises() {
        exercise_type = "core";
        image = R.drawable.exercise_core_leg_raises;
        workoutTime = 50000;
        exerciseName = "Leg raises";
    }
    public void set_elbow_to_knee() {
        exercise_type = "core";
        image = R.drawable.exercise_core_elbow_to_knee;
        workoutTime = 45000;
        exerciseName = "Elbow to knee";
    }


    // setters
    public void setWorkout_level(float level) {
        workout_level = level;
    }


    // getters
    public int getWorkoutTime() {
        return (int)(workout_level * workoutTime);
    }

    public int get_image() {
        return image;
    }

    public String getExerciseName(){
        return exerciseName;
    }
}
