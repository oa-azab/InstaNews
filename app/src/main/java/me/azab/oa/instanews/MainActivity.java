package me.azab.oa.instanews;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Story>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Constant value for story loader ID
    private static final int STORY_ASYNC_TASK_LOADER_ID = 1;

    // Test url
    private static final String TEST_URL = "https://content.guardianapis.com/search?q=egypt&api-key=test&order-by=newest";

    // Stories recycler view
    private RecyclerView storyRecyclerView;

    // Stories recycler view adapter
    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    // List of stories objects
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI
        storyRecyclerView = (RecyclerView) findViewById(R.id.story_recycler_view);

        // Initialize Story Adapter with empty
        storyList = new ArrayList<>();
        storyList.add(new Story("You're stories will show here soon","","","url"));
        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter(this, storyList);

        // Attach the adapter to the recyclerview to populate items
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        // Set layout manager to position the items
        storyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Optimizations for significantly smoother scrolling
        storyRecyclerView.setHasFixedSize(true);

        // Get LoaderManager reference and int loader
        getLoaderManager().initLoader(STORY_ASYNC_TASK_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
        // TODO: send query url
        return new StoryAsyncTaskLoader(MainActivity.this,TEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
        // TODO: update UI here
        storyList.clear();
        storyList.addAll(data);
        storyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        // TODO: remove data from UI
        storyList.clear();
    }
}
