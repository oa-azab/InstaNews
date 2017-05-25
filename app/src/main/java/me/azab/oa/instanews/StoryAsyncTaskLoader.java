package me.azab.oa.instanews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 5/26/2017.
 */


/**
 *  AysncTaskLoader class that fetch stories from api
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
        Log.d("LoaderTest","StoryAsyncTaskLoader Constructor");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.d("LoaderTest","onStartLoading forceLoad");
    }

    @Override
    public List<Story> loadInBackground() {
        List<Story> list = new ArrayList<>();
        list.add(new Story());
        list.add(new Story());
        Log.d("LoaderTest","loadInBackground");
        return list;
    }
}
