package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Αυτή η κλάση αναπαριστά μια σεζόν μιας σειράς
 */
public class Season implements Serializable{
    private int seasonNumber;
    private int year;
    private ArrayList<Episode> episodes;

    /**
     * Ο κατασκευαστής της κλάσης
     * @param seasonNumber  Ο αριθμός της σεζόν
     * @param year η χρονιά που κυκλοφόρησε
     */
    public Season(int seasonNumber, int year) {
        this.seasonNumber = seasonNumber;
        this.year = year;
        episodes = new ArrayList<>();
    }

    public Season(){
        episodes = new ArrayList<>();
    }


    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
    public int getSeasonNumber() {
        return seasonNumber;
    }


    public void setYear(int year) {this.year = year;}
    public int getYear() {return year;}

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }
    public void deleteEpisode(Episode episode){
        episodes.remove(episode);
    }
    public ArrayList<Episode> getEpisodes(){
        return episodes;
    }

    @Override
    public String toString() {
        return seasonNumber +" " + year + " " + episodes.size()+" episodes";
    }
}
