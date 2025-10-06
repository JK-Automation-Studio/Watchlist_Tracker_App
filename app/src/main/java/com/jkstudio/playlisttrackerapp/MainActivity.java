package com.jkstudio.playlisttrackerapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    Library library;
    ListingViewAdapter adapter;
    TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Run default onCreate methods before our own
        super.onCreate(savedInstanceState);
        // Edge to Edge, IDK if needed
        EdgeToEdge.enable(this);
        // Set content view to main activity's xml file
        setContentView(R.layout.activity_main);




        // create new library and fill with temp Listings
        // TODO Replace with retrieving library from file
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


        // Sets visibility of empty list hint text
        updateEmptyView();


        // Create recycler view and set layout to linear
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Use adapter class to update recycle views
        adapter = new ListingViewAdapter(this, library.getListings());
        recyclerView.setAdapter(adapter);
    }

    public void updateEmptyView() {

        // Set variable for text field
        emptyText = findViewById(R.id.textViewEmptyList);

        // If library is empty, show empty list hint
        if(library.getCount()==0){
            emptyText.setVisibility(View.VISIBLE);
        }
        else{
            // if library not empty, hide list hint and log library's count
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

        // Set variables for editable attributes
        EditText editTitle = dialogView.findViewById(R.id.editTextTitle);
        EditText editWatchMethod = dialogView.findViewById(R.id.editTextWatchMethod);
        ImageView imageView = dialogView.findViewById(R.id.imageView);

        // Set variables for buttons in dialog
        Button btnYes = dialogView.findViewById(R.id.buttonAdd);
        Button btnNo = dialogView.findViewById(R.id.buttonCancel);

        // Fill edit dialog fields with listing's current info, new Listings are blank
        editTitle.setText(listing.getTitle());
        editWatchMethod.setText(listing.getWatchMethod());
        imageView.setImageResource(R.drawable.default_listing_photo); // set image to default

        //TODO Set photo attribute with Listing's photo chosen by user, if there


        // Check for current listing info, if empty is new Listing.
        // Currently checking title to set photo, later will check photo attribute
        if(listing.getTitle().isEmpty()) {
            imageView.setImageResource(R.drawable.add_photo_bg); // set image to green grid
            btnYes.setText("Add");
        }
        else{
            btnYes.setText("Confirm");
        }
        // Build the dialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();


        // Settings for dialog window, attempt for keyboard to not push alert around
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        //Click listener for edit dialog add/confirm button
        btnYes.setOnClickListener(v -> {
            // Title and method attributes from dialog
            String title = editTitle.getText().toString().trim();
            String method = editWatchMethod.getText().toString().trim();

            // if title has text, set listing's fields
            if (!title.isEmpty()) {
                listing.setTitle(title);
                listing.setWatchMethod(method);

                // Add to library if this is a new listing
                if (!library.getListings().contains(listing)) {
                    library.addListing(listing);
                    updateLibraryFile();
                }

                // Use the adapter to update RecyclerView of changes
                adapter.notifyDataSetChanged();

                // Update empty text if needed and close the popup
                updateEmptyView();
                dialog.dismiss();
            } else {
                // Title is empty, write error to text box
                editTitle.setError("Title required"); // Show error in text box if empty title
            }
        });


        // Track clicks on Negative button, update Empty Text if needed and dismiss dialog
        btnNo.setOnClickListener(v -> {
            updateEmptyView();
            dialog.dismiss();
        });

        // Set dialog bounding box to invisible
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Set background dim amount
        dialog.getWindow().setDimAmount(0.65f);

        // Finally show dialog on screen
        dialog.show();

        // Focus keyboard cursor on Title box
        editTitle.setFocusable(true);
        editTitle.setFocusableInTouchMode(true);
        editTitle.requestFocus();

        // Set SoftwareInputMode to visible, then schedule a post in
        // EditTitle to create InputManager for showing Software Keyboard
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editTitle.post(new Runnable() {
            @Override public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTitle, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }





    private void updateLibraryFile() {
        // TODO Create method for writing Library's listings to Library File in storage
    }

    public void retrieveLibraryFile(){
        // TODO Create method for retrieving Library from Library File in storage
    }

    public void onPhotoClick(View v){
        // TODO If needed a method for creating photo clicker popup from photo click
    }

}