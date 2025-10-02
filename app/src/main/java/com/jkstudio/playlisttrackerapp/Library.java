package com.jkstudio.playlisttrackerapp;

import java.util.ArrayList;


/*
USAGE
Create Library object called Movie
Set Library Title to Movies
AddListing() * n

Show Listings
 */
public class Library {
    private final ArrayList<Listing> listings;
    private String title;
    private int count;

    public Library(){
        this.listings = new ArrayList();
        this.title = "Watchlist";
        this.count = 0;
    }


    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        // TODO Verification
        this.title = title;
    }

    public void addListing(Listing listing){
        //TODO check ids for uniqueness, update this listing's id if not

        // Add item to arraylist
        this.listings.add(listing);
        this.count++;
    }

    public void removeListing(Listing listing){
        // Remove item from arraylist
        this.listings.remove(listing);
        this.count--;
    }

    public Listing getListingByID(int id){
        return(this.listings.get(id));

    }

    public int getCount(){
        return(this.count);
    }

    public ArrayList getListings(){
        return(this.listings);
    }
}
