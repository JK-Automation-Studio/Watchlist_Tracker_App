package com.jkstudio.playlisttrackerapp;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

public class Listing {

    // Class Attributes
    private int id;
    private String title;
    private String watchMethod;
    private String subTitle;
    private int rating;
    private String description;
    boolean watched;
    private String photo;

    // JSON Key Titles
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    // private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_WATCH_METHOD = "watch-method";
    private static final String JSON_WATCHED = "todo";
    private static final String JSON_PHOTO = "important";


    //USAGE
    /*
    Create new listing
    Set ID, Title, WatchMethod, Photo, etc
    Add listing to Library (Movie or TV)
     */
    public Listing(){
        // Default Attributes
        this.id = -1;
        this.title = "";
        this.watchMethod = "";
        this.subTitle = "";
        this.rating = 0;
        this.description = "";
        this.watched = false;
        this.photo = "";
    }
    // Constructor
    // Only used when new is called with a JSONObject
    public Listing(JSONObject jo) throws JSONException {

        id =  jo.getInt(JSON_ID);
        title =  jo.getString(JSON_TITLE);
        watchMethod = jo.getString(JSON_WATCH_METHOD);
        watched = jo.getBoolean(JSON_WATCHED);
        photo = jo.getString(JSON_PHOTO);

    }

    // Convert to JSON
    public JSONObject convertToJSON() throws JSONException {

        JSONObject jo = new JSONObject();
        jo.put(JSON_ID, id);
        jo.put(JSON_TITLE, title);
        jo.put(JSON_WATCH_METHOD, watchMethod);
        jo.put(JSON_WATCHED, watched);
        jo.put(JSON_PHOTO, this.getPhoto());
        return jo;
    }

    // Getters and Setters
    public int getId(){
        return(this.id);
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle(){
        return(this.title);
    }
    public void setTitle(String name){
        // TODO Verification
        this.title = name;
    }

    public String getWatchMethod(){
        return(this.watchMethod);
    }
    public void setWatchMethod(String method){
        // TODO Verification
        this.watchMethod= method;
    }

    public String getSubTitle(){
        return(this.subTitle);
    }
    public void setSubTitle(String text){
        // TODO Verification
        this.subTitle = text;
    }

    public int getRating(){
        return(this.rating);
    }
    public void setRating(int rate){
        // TODO Verification
        this.rating = rate;
    }

    public String getDescription(){
        return(this.description);
    }
    public void setDescription(String text){
        // TODO Verification
        this.description = text;
    }

    public Boolean getWatched(){
        return(this.watched);
    }
    public void setWatched(Boolean watch){
        // TODO Verification
        this.watched = watch;
    }
    public String getPhoto(){

        return(this.photo);
    }
    public void setPhoto(String path){
        // TODO Verification
        this.photo = path;
    }
}
