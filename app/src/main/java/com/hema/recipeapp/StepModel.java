package com.hema.recipeapp;

/**
 * Created by hema on 9/27/2017.
 */

public class StepModel {

    int id;
    String shortDescription;
    String description;
    String videoURL;
    String photo;


    public StepModel(int mId, String mShortDescription, String mDescription, String mVideoURL, String p) {
        id = mId;
        shortDescription = mShortDescription;
        description = mDescription;
        videoURL = mVideoURL;
        photo=p;
    }
    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String mShortDescription) {
        shortDescription = mShortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String mDescription) {
        description = mDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String mVideoURL) {
        videoURL = mVideoURL;
    }
}
