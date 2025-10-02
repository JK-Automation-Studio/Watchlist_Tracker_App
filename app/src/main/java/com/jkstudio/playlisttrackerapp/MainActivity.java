package com.jkstudio.playlisttrackerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    Library library;
    ListingViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        library = new Library();

        Listing listing = new Listing();
        listing.setId(100);
        listing.setTitle("Guardians of the Multiverse");
        listing.setDescription("A movie about a group of people who accomplish a goal.");
        //listing.setRating(3);
        listing.setPhoto("Placeholder Image");
        listing.setWatched(false);
        listing.setWatchMethod("Disney+");
        library.addListing(listing);

        Listing listing2 = new Listing();
        listing2.setId(200);
        listing2.setTitle("Star Wars Trek");
        listing2.setDescription("A movie about a group of people who accomplish a goal.");
        //listing2.setRating(5);
        listing2.setPhoto("Placeholder Image");
        listing2.setWatched(false);
        listing2.setWatchMethod("Netflix UK");
        library.addListing(listing2);

        Listing listing3 = new Listing();
        listing3.setId(300);
        listing3.setTitle("The Man Bat Beyond");
        listing3.setDescription("A movie about a group of people who accomplish a goal.");
        //listing3.setRating(5);
        listing3.setPhoto("Placeholder Image");
        listing3.setWatched(false);
        listing3.setWatchMethod("HBO Max");
        library.addListing(listing3);

        Listing listing4 = new Listing();
        listing4.setId(300);
        listing4.setTitle("Field Sign");
        listing4.setDescription("A movie about a group of people who accomplish a goal.");
        //listing4.setRating(0);
        listing4.setPhoto("Placeholder Image");
        listing4.setWatched(false);
        listing4.setWatchMethod("Unknown");
        library.addListing(listing4);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListingViewAdapter(this, library.getListings());
        recyclerView.setAdapter(adapter);




    }
    public void onFabClick(View v){
        EditListing(new Listing());
    }

    public void EditListing(Listing listing) {
        //TODO Scene for editing a Listing / Creating
        // Edit Text boxes for all texts
        // edit photo option somehow

        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_listing, null);

        EditText editTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editWatchMethod = dialogView.findViewById(R.id.editTextWatchMethod);

        Button btnAdd = dialogView.findViewById(R.id.buttonAdd);
        Button btnCancel = dialogView.findViewById(R.id.buttonCancel);

        // If editing an existing listing, pre-fill fields
        editTitle.setText(listing.getTitle());
        editWatchMethod.setText(listing.getWatchMethod());
        //editRating.setText(String.valueOf(listing.getRating()));
        //editDescription.setText(listing.getDescription());



        // Build the dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setTitle("Edit Listing")
                .setView(dialogView)
                .create();


        btnAdd.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String method = editWatchMethod.getText().toString().trim();
            //String ratingStr = editRating.getText().toString().trim();

            if (!title.isEmpty()) {
                listing.setTitle(title);
                listing.setWatchMethod(method);

                // Add to library if this is a new listing
                if (!library.getListings().contains(listing)) {
                    library.getListings().add(listing);
                }

                // Update RecyclerView
                adapter.notifyDataSetChanged();

                // Close the popup
                dialog.dismiss();
            } else {
                editTitle.setError("Title required");
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

    public void retrieveListings(){
        //TODO Get file from storage
        // get Listings from file
        // set Library with all listings
    }

    public void onPhotoClick(View v){

    }

    public void onClickAdd(View v){
        //TODO get current listing's stuff from dialog_edit_listing
        // create new listing with info
        // add to library
        // update recycler
        // Save entered data back into Listing


    }

    public void onClickCancel(View v){
        //TODO Remove dialog_edit_listing view from screen

    }
}