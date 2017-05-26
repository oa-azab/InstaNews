package me.azab.oa.instanews;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Story>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Constant value for story loader ID
    private static final int STORY_ASYNC_TASK_LOADER_ID = 1;

    // Test url
    private static final String TEST_URL = "https://content.guardianapis.com/search?q=egypt&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        // TODO: remove data from UI
    }
}
