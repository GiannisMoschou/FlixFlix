package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Αυτή η κλάση αναπαριστά μια σειρά
 */
public class Series extends Content implements Serializable {
    private ArrayList<Season> seasons;

    public Series(String title, String description, boolean isOverEighteen, Category category, String protagonists) {
        super(title, description, isOverEighteen, category, protagonists);
    }

    public Series(){
        seasons = new ArrayList<>();
        alike =  new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public void addSeason(Season season){
        seasons.add(season);
    }
    public void deleteSeason(Season season){
        seasons.remove(season);
    }
    public ArrayList<Season> getSeasons(){
        return seasons;
    }

    @Override
    public String toString() {
        return getTitle()+" "+ seasons.size()+" " + "Seasons";
    }
}
