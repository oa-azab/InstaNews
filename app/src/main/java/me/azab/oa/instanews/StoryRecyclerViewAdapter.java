package me.azab.oa.instanews;

/**
 * Created by omar on 5/26/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * StoryRecyclerViewAdapter class that handel
 */
public class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder> {

    // List of story objects holding story info
    private List<Story> storyList;

    // Context of activity calling this recycler view adapter
    private Context context;

    // Constructor passing context and list of stories to be displayed
    public StoryRecyclerViewAdapter(Context context, List<Story> storyList) {
        this.storyList = storyList;
        this.context = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View storyView = inflater.inflate(R.layout.item_story, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(storyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get current story object
        Story story = storyList.get(position);

        // Set item views based on views and data model
        TextView titleIextView = holder.titleTextView;
        titleIextView.setText(story.getTitle());
        TextView sectionIextView = holder.sectionTextView;
        sectionIextView.setText(story.getSection());
        TextView dateIextView = holder.dateTextView;
        dateIextView.setText(story.getDate());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView sectionTextView;
        public TextView dateTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.item_title);
            sectionTextView = (TextView) itemView.findViewById(R.id.item_section);
            dateTextView = (TextView) itemView.findViewById(R.id.item_date);

            // on item click open story in browser
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Story story = storyList.get(position);

                        // Convert the String URL into a URI object (to pass into the Intent constructor)
                        Uri storyUri = Uri.parse(story.getUrl());

                        // Create a new intent to view the story URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, storyUri);

                        // Send the intent to launch a new activity
                        context.startActivity(websiteIntent);
                    }
                }
            });
        }
    }
}

