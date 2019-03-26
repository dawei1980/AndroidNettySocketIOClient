package com.socketio.client.entity;

import java.io.Serializable;

public class Recommendation implements Serializable {

    private String timeouttime;
    private String getrecommendationtime;
    private String getrecommendationinterval;
    private String updatetime;
    private String starttime;
    private String cameragroup;

    public String getCameragroup() {
        return cameragroup;
    }

    public void setCameragroup(String cameragroup) {
        this.cameragroup = cameragroup;
    }

    public String getTimeouttime() {
        return timeouttime;
    }

    public void setTimeouttime(String timeouttime) {
        this.timeouttime = timeouttime;
    }

    public String getGetrecommendationtime() {
        return getrecommendationtime;
    }

    public void setGetrecommendationtime(String getrecommendationtime) {
        this.getrecommendationtime = getrecommendationtime;
    }

    public String getGetrecommendationinterval() {
        return getrecommendationinterval;
    }

    public void setGetrecommendationinterval(String getrecommendationinterval) {
        this.getrecommendationinterval = getrecommendationinterval;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
}
