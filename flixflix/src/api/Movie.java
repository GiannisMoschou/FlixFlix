package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Αυτή η κλάση αναπαριστά μια ταινία
 */
public class Movie extends Content implements Serializable {
    private int year;
    private int durationInMinutes;

    public Movie(String title,String description,boolean isOver18,Category category,String protagonists,int year, int durationInMinutes) {
        super(title,description,isOver18,category,protagonists);
        this.year = year;
        this.durationInMinutes = durationInMinutes;
    }


    public Movie(){
        alike = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public void setYear(int year){
        this.year = year;
    }
    public int getYear(){
        return year;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    @Override
    public String toString() {
        return getTitle() + " " + getYear();
    }
}
