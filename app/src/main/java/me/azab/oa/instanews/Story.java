package me.azab.oa.instanews;

/**
 * Created by omar on 5/26/2017.
 */

/**
 * Model class represent a story of news
 *
 */
public class Story {

    // title of the story
    private String title;

    // name of the section this story is under
    private String section;

    // date of publishing
    private String date;

    // url of this story on the web
    private String url;

    // Empty constructor
    public Story() {
    }

    // Constructor to set all values
    public Story(String title, String section, String date, String url) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.url = url;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
