package api;

import java.io.Serializable;

/**
 * Αυτή η κλάση αναπαριστά ένα επισόδιο.
 * Κάθε επισόδιο έχει έναν τίτλο και τη διάρκειά του.
 */
public class Episode implements Serializable {
    private String title;
    private int duration;

    public Episode(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {this.duration = duration;}
    public int getDuration() { return duration;}

    @Override
    public String toString() {
        return title + " " + duration + " minutes";
    }
}
