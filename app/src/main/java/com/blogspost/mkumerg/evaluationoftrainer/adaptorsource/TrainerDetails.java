package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

/**
 * Created by mkumerg on 10/19/15.
 */
public class TrainerDetails {
    private String trainerName;
    private String trainerDescription;
    private String trainerId;

    public String getTrainerDescription() {
        return trainerDescription;
    }

    public void setTrainerDescription(String trainerDescription) {
        this.trainerDescription = trainerDescription;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {

        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public String toString() {
        return "TrainerDetails{" +
                "trainerName='" + trainerName + '\'' +
                ", trainerDescription='" + trainerDescription + '\'' +
                '}';
    }
}
