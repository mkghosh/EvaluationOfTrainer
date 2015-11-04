package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

/**
 * Created by mkumerg on 10/17/15.
 */
public class CourseNameAndDescription {
    String courseName;
    String courseDescription;
    String autoId;

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public String toString() {
        return "course : " + courseName  + ", Description : " + courseDescription;
    }
}
