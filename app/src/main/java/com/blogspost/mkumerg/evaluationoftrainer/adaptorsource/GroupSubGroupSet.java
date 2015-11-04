package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

/**
 * Created by mkumerg on 10/7/15.
 */
public class GroupSubGroupSet {

    private String group;
    private String subGroup;
    private String property;
    private String propertyId;
    private int rating1;
    private int rating2;
    private int rating3;
    private int rating4;
    private int rating5;

    public int getRating1() {
        return rating1;
    }

    public void setRating1(int rating1) {
        this.rating1 = rating1;
    }

    public int getRating2() {
        return rating2;
    }

    public void setRating2(int rating2) {
        this.rating2 = rating2;
    }

    public int getRating3() {
        return rating3;
    }

    public void setRating3(int rating3) {
        this.rating3 = rating3;
    }

    public int getRating4() {
        return rating4;
    }

    public void setRating4(int rating4) {
        this.rating4 = rating4;
    }

    public int getRating5() {
        return rating5;
    }

    public void setRating5(int rating5) {
        this.rating5 = rating5;
    }


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public String toString() {
        return "GroupSubGroupSet{" +
                "group='" + group + '\'' +
                ", subGroup='" + subGroup + '\'' +
                ", property='" + property + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", rating1=" + rating1 +
                ", rating2=" + rating2 +
                ", rating3=" + rating3 +
                ", rating4=" + rating4 +
                ", rating5=" + rating5 +
                '}';
    }
}
