package com.jkstudio.playlisttrackerapp;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);




        // create new library and fill with temp Listings
        // Replace with retrieving library from file
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



        // get recyclerView and
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListingViewAdapter(this, library.getListings());
        recyclerView.setAdapter(adapter);



        updateEmptyView();


    }

    public void updateEmptyView() {
        emptyText = findViewById(R.id.textViewEmptyList);

        if(library.getCount()==0){
            emptyText.setVisibility(View.VISIBLE);
        }
        else{
            emptyText.setVisibility(View.INVISIBLE);
            Log.i("i",""+library.getCount());
        }
    }

    public void onFabClick(View v){
        EditListing(new Listing());
    }

    public void EditListing(Listing listing) {
        //TODO edit photo option somehow

        // Dialog Edit Listing
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_listing, null);

        EditText editTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editWatchMethod = dialogView.findViewById(R.id.editTextWatchMethod);
        ImageView imageView = dialogView.findViewById(R.id.imageView);

        Button btnYes = dialogView.findViewById(R.id.buttonAdd);
        Button btnNo = dialogView.findViewById(R.id.buttonCancel);

        // Fill edit dialog fields with listing's current info, new Listings are blank
        editTitle.setText(listing.getTitle());
        editWatchMethod.setText(listing.getWatchMethod());
        // Title requests focus for easy access..?








        if(listing.getTitle().isEmpty()) {
            imageView.setImageResource(R.drawable.add_photo_bg); // set image to green grid
            btnYes.setText("Add");
        }
        else{
            imageView.setImageResource(R.drawable.default_listing_photo); // set image to default
            //TODO photo storage handled properly, set the image here to the Listing's photo

            btnYes.setText("Confirm");
        }
        // Build the dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setTitle("Edit Listing")
                .setView(dialogView)
                .create();


        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        //Click listener for edit dialog add button
        btnYes.setOnClickListener(v -> {
            // Title and method attributes from dialog
            String title = editTitle.getText().toString().trim();
            String method = editWatchMethod.getText().toString().trim();

            if (!title.isEmpty()) {
                listing.setTitle(title);
                listing.setWatchMethod(method);

                // Add to library if this is a new listing
                if (!library.getListings().contains(listing)) {
                    library.addListing(listing);
                    updateLibraryFile();
                }

                // Update RecyclerView
                adapter.notifyDataSetChanged();

                // Update empty text if needed and close the popup
                updateEmptyView();
                dialog.dismiss();
            } else {
                editTitle.setError("Title required"); // Show error in text box if empty title
            }
        });


        // Track clicks on Negative button, update Empty Text if needed and dismiss dialog
        btnNo.setOnClickListener(v -> {
            updateEmptyView();
            dialog.dismiss();
        });

        // Finally show dialog on screen
        dialog.show();

        editTitle.setFocusable(true);
        editTitle.setFocusableInTouchMode(true);
        editTitle.requestFocus();

        editTitle.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editTitle.post(new Runnable() {
            @Override public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTitle, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }





    private void updateLibraryFile() {
        // TODO Create method for writing Library to libraryFile
    }

    public void retrieveLibraryFile(){
        // TODO Create method for retrieving Library from libraryFile
    }

    public void onPhotoClick(View v){
        // TODO IDK how to do photo yet so this is here JIC
    }

}