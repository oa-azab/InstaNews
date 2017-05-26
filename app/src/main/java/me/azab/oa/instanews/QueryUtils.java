package me.azab.oa.instanews;

/**
 * Created by omar on 5/26/2017.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving data from api.
 */
public class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * A private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods
     */
    private QueryUtils() {
    }

    /**
     * Query Guardian api and return list of {@link Story} objects
     *
     * @param queryUrl query url to fetch data from api
     * @return list of {@link Story} objects
     */
    public static List<Story> fetchStoryData(String queryUrl) {
        // Create URL object
        URL url = createUrl(queryUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract data from json response into list of{@link Story} objects
        List<Story> stories = extractStoriesFromJson(jsonResponse);

        // Return the list of of{@link Story} objeckts
        return stories;
    }

    /**
     * Parse json response to list of {@link Story} objects
     *
     * @param jsonResponse full json response as string
     * @return list of {@link Story} objects
     */
    public static List<Story> extractStoriesFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding stories to
        List<Story> stories = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject rootObject = new JSONObject(jsonResponse);

            // Get response JSONObject
            JSONObject responseObject = rootObject.getJSONObject("response");

            // Get result JSONArray of stories
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // For each story in result array create Story object and added to stories list
            for (int i = 0; i < resultsArray.length(); i++) {

                // Get a single story at position i within the list of results
                JSONObject storyObject = resultsArray.getJSONObject(i);

                // Extract story title
                String title = storyObject.getString("webTitle");

                // Extract section name
                String section = storyObject.getString("sectionName");

                // Extract publish date formatted
                String date = storyObject.getString("webPublicationDate").substring(0,10);

                // Extract web url
                String url = storyObject.getString("webUrl");

                // Create new {@link Story) object and initialize it with extracted data
                Story story = new Story(title,section,date,url);

                // Add {@link Story) object to stories list
                stories.add(story);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // return list of {@link Story} objects
        return stories;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
}
