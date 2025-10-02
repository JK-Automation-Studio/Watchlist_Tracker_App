package com.jkstudio.playlisttrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter connects your data (List<Listing>) with the RecyclerView
public class ListingViewAdapter extends RecyclerView.Adapter<ListingViewAdapter.ListingViewHolder> {

    private final MainActivity mainActivity;            // activity/fragment context
    private final ArrayList<Listing> listings;     // the data source

    // Constructor: pass in context + data
    public ListingViewAdapter(MainActivity mainActivity, ArrayList<Listing> listings) {
        this.mainActivity = mainActivity;
        this.listings = listings;
    }

    // 1. Called when a new ViewHolder (card) needs to be created
    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your card layout (cardview_listing.xml)
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.cardview_listing, parent, false);


        return new ListingViewHolder(view);
    }


    // 2. Called when data needs to be bound to a ViewHolder (fill in card with Listing info)
    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        Listing listing = listings.get(position);

        // Set text
        holder.textTitle.setText(listing.getTitle());
        holder.textWatchMethod.setText(listing.getWatchMethod());

        // TODO: load image from listing.getPhoto() - API or Local Image or something else?
        holder.imagePoster.setImageResource(R.drawable.ic_launcher_foreground); // set card photo to droid

        // Example button usage:
        holder.btnWatched.setText(listing.getWatched() ? "Watched" : "Not Watched");

        // Handle clicks
        holder.btnWatched.setOnClickListener(v -> {
            listing.setWatched(!listing.getWatched()); // toggle watched state
            notifyItemChanged(position); // refresh this card
        });

        holder.btnRemove.setOnClickListener(v -> {
            //listings.remove(position);   // remove this movie from the list
            mainActivity.library.removeListing(position);
            mainActivity.updateEmptyView();
            notifyItemRemoved(position); // notify RecyclerView
            notifyItemRangeChanged(position, listings.size());
        });

        holder.infoButton.setOnClickListener(view -> {
            mainActivity.EditListing(listing);
        });

    }
    // Callback interface
    public interface OnListingClickListener {
        void onEditClick(Listing listing);
    }
    // 3. Tells RecyclerView how many cards it needs
    @Override
    public int getItemCount() {
        return listings.size();
    }

    // ------------------------
    // ViewHolder Inner Class
    // ------------------------
    public static class ListingViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textWatchMethod, textEmpty;
        ImageView imagePoster;
        ImageButton infoButton; // e.g. menu or overflow button
        Button btnWatched, btnRemove;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find all views inside cardview_listing.xml
            textTitle = itemView.findViewById(R.id.textListingTitle);
            infoButton = itemView.findViewById(R.id.infoButton);

            imagePoster = itemView.findViewById(R.id.imageView);

            textWatchMethod = itemView.findViewById(R.id.textWatchMethod);

            textEmpty = itemView.findViewById(R.id.textViewEmptyList);

            btnRemove = itemView.findViewById(R.id.buttonCancel);
            btnWatched = itemView.findViewById(R.id.buttonAdd);
        }
    }
}
