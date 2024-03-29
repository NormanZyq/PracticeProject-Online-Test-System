package com.neuedu.project.domain;

public class Arrangement {

    private int testId;

    private String courseName = "";

    private String startTime;

    private int duration;

    private int identity = 0;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public Arrangement(int testId, String startTime, int duration) {
        this.testId = testId;
        this.startTime = startTime;
        this.duration = duration;
    }
    public Arrangement(){

    }

    @Override
    public String toString() {
        return "Arrangement{" +
                "testId=" + testId +
                ", courseName='" + courseName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration=" + duration +
                ", identity=" + identity +
                '}';
    }
}
