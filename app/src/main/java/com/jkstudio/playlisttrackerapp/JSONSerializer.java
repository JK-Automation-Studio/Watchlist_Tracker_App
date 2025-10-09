package com.jkstudio.playlisttrackerapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONSerializer {
    private String mFilename;
    private Context mContext;

    // Constructor
    public JSONSerializer(String fn, Context con){
        mFilename = fn;
        mContext = con;
    }

    public void save(ArrayList<Listing> listings) throws IOException, JSONException {
        // Make an array in JSON format
        JSONArray lArray = new JSONArray();

        // And load it with the listings
        for (Listing l : listings) {
            lArray.put(l.convertToJSON());
        }
        // Now write it to the private disk space of our app
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, mContext.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(lArray.toString());
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    public ArrayList<Listing> load() throws IOException, JSONException{
        ArrayList<Listing> listingList = new ArrayList<Listing>();
        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            // Get all lines from reader, add to jsonString StringBuilder
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            // Set JSONArray from jsonString
            JSONArray lArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // Add JSONObject from jArray to new Listing and add to listingList
            for (int i = 0; i < lArray.length(); i++) {
                listingList.add(new Listing (lArray.getJSONObject(i)));
            }
        }
        catch (FileNotFoundException e) {
            // we will ignore this one, since it happens
            // when we start fresh. You could add a log here.
        }
        finally {// This will always run
            if (reader != null)
                reader.close();
        }

        if(!listingList.get(1).getTitle().isBlank()){
            Collections.reverse(listingList);
        }
        
        return (ArrayList<Listing>) listingList;
    }


} // End of class
