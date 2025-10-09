package com.jkstudio.playlisttrackerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  JSONSerializer mSerializer;
    private List<Listing> listingList;

    Library library;
    ListingViewAdapter adapter;
    TextView emptyText;
    TextView textEmptyPhoto;
    ImageView imageView;
    String uri;
    Listing listing;

    private static final int PICK_IMAGE_REQUEST = 1;



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



        // Serializer to read notes
        mSerializer = new JSONSerializer("LibraryListings.json", getApplicationContext());
        try {
            listingList = mSerializer.load();


            for(Listing l : listingList)
            {
                library.addListing(l);
            }

            Log.i("i","Library Loaded");
        } catch (Exception e) {
            Log.e("Error loading library: ", "", e); // Log to logcat
        }

        // Sets visibility of empty list hint text
        updateEmptyView();




        // Create recycler view and set layout to linear
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Use adapter class to update recycle views
        adapter = new ListingViewAdapter(this, library.getListings());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPause(){
        // Save notes when app is closed
        super.onPause();
        writeLibraryFile();
    }
    public void updateEmptyView() {

        // Set variable for text field
        emptyText = findViewById(R.id.textViewEmptyList);

        // If library is empty, show empty list hint
        if(library.getCount()==0){
            emptyText.setVisibility(View.VISIBLE);
            Log.i("i","Library Count:\n"+library.getCount()); // Logcat message showing size of Library
        }
        else{
            // if library not empty, hide list hint and log library's count
            emptyText.setVisibility(View.INVISIBLE);
            Log.i("i","Library Count:\n"+library.getCount()); // Logcat message showing size of Library
        }
    }

    public void onFabClick(View v){
        // Floating Action Button click, edit listing with New listing for Add Listing
        EditListing(new Listing());
    }

    public void EditListing(Listing listing) {
        // Dialog Edit Listing
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_edit_listing, null);

        // Set variables for editable attributes
        EditText editTitle = dialogView.findViewById(R.id.editListingTitleDialog);
        EditText editWatchMethod = dialogView.findViewById(R.id.editWatchMethodDialog);
        imageView = dialogView.findViewById(R.id.imageViewDialog);

        imageView.setOnClickListener(v -> openImageChooser());


        // Set variables for buttons in dialog
        Button btnYes = dialogView.findViewById(R.id.buttonAddDialog);
        Button btnNo = dialogView.findViewById(R.id.buttonCancelDialog);
        textEmptyPhoto = dialogView.findViewById(R.id.textEmptyPhotoDialog);

        // Fill edit dialog fields with listing's current info, new Listings are blank
        editTitle.setText(listing.getTitle());
        editWatchMethod.setText(listing.getWatchMethod());
        //imageView.setImageResource(R.drawable.default_listing_photo); // set image to default

        // Check if listing's photo has been set yet, if null, new listing
        if(listing.getPhoto() == "")
        {
            imageView.setImageResource(R.drawable.add_photo_bg); // set image to green grid
            textEmptyPhoto.setVisibility(View.VISIBLE);

        }
        else{
            imageView.setImageURI(Uri.parse(listing.getPhoto()));
            textEmptyPhoto.setVisibility(View.INVISIBLE);
        }


        // Check for current listing info, if empty is new Listing.
        if(listing.getTitle().isEmpty()) {
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
            Object uri = imageView.getTag();

            // if title has text, set listing's fields
            if (!title.isEmpty()) {
                listing.setTitle(title);
                listing.setWatchMethod(method);

                if(uri == null) {
                }
                else {
                    listing.setPhoto(imageView.getTag().toString()); // Get photo URI from imageView and save to listing
                }

                // Add to library if this is a new listing
                if (!library.getListings().contains(listing)) {
                    library.addListing(listing);
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

        // Set SoftwareInputMode to visible, then schedule a post in EditTitle to create InputManager for showing Software Keyboard
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editTitle.post(new Runnable() {
            @Override public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTitle, InputMethodManager.SHOW_IMPLICIT);
            }
        });


    }


    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Get the result of photo picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                imageView.setImageURI(selectedImageUri);
                imageView.setTag(selectedImageUri.toString());
                textEmptyPhoto.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void writeLibraryFile(){
        try{
            mSerializer.save(library.getListings());
            Log.i("i","Saved Library");

        }
        catch(Exception e){
            Log.i("i","Error Saving Library");
        }
    }

    public void readLibraryFile(){
        // TODO Create method for retrieving Library from Library File in storage
    }


}