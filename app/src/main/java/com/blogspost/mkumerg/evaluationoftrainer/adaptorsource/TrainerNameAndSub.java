package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

/**
 * Created by mkumerg on 10/15/15.
 */
public class TrainerNameAndSub {
    String trainerName;
    String subject;
    String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String toString(){
        return "Trainer Name: " + this.trainerName + " " + "Subject: " + this.subject;
    }
}
