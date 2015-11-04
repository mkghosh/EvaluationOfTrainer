package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

/**
 * Created by mkumerg on 10/19/15.
 */
public class SessionDetails {
    private String trackName;
    private String groupName;
    private String trainerName;
    private String sessionId;
    private String workDate;
    private String courseName;
    private String comment;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SessionDetails{" +
                "trackName='" + trackName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", comment='" + comment + '\'' +
                ", workDate='" + workDate + '\'' +
                '}';
    }
}
