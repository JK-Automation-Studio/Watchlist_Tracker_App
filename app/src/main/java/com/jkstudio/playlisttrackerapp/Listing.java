package com.jkstudio.playlisttrackerapp;

public class Listing {
    private int id;
    private String title;
    private String watchMethod;
    private String subTitle;
    private int rating;
    private String description;
    boolean watched;
    private String photo;


    //USAGE
    /*
    Create new listing
    Set ID, Title, WatchMethod, Photo, etc
    Add listing to Library (Movie or TV)
     */
    public Listing(){
        this.id = -1;
        this.title = "";
        this.watchMethod = "";
        this.subTitle = "";
        this.rating = 0;
        this.description = "";
        this.watched = false;
        this.photo = "";
    }

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
