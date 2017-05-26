package me.azab.oa.instanews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by omar on 5/26/2017.
 */


/**
 *  AsyncTaskLoader class that fetch stories from api
 *
 */
public class StoryAsyncTaskLoader extends AsyncTaskLoader<List<Story>> {

    /** Tag for log messages */
    private static final String LOG_TAG = StoryAsyncTaskLoader.class.getSimpleName();

    // Query url
    private String mUrl;

    /**
     *  Construct new {@link StoryAsyncTaskLoader}.
     *
     * @param context context of activity
     * @param url query url to load data from
     */
    public StoryAsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        List<Story> list = QueryUtils.fetchStoryData(mUrl);
        return list;
    }
}
