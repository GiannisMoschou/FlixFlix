package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Η κλάση "γονέας" των ταινιών και των σειρών που περιέχει τα κοινά τους πεδία και μεθόδους
 */
public abstract class Content implements Serializable {
    private String title;
    private String description;
    private boolean isOverEighteen;
    private Category category;
    private String protagonists;

    /**
     * μια λίστα στην οποία αποθηκέυοντα αντικείμενα τύπου Rating ,
     * <p>
     * δηλαδή οι αξιολογήσεις για κάθε ταινία η σειρά.
     */
    protected ArrayList<Rating> ratings;
    /**
     * μια λίστα που περιέχει σχετικές ταινίες ή σειρές
     * σε σχέση με το περιεχώμενο.
     */
    protected ArrayList<Content> alike;//λίστα σχετικών σειρών η ταινιων

    public Content(){
        alike = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public Content(String title, String description, boolean isOverEighteen, Category category, String protagonists) {
        this.title = title;
        this.description = description;
        this.isOverEighteen = isOverEighteen;
        this.category = category;
        this.protagonists = protagonists;
        alike = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setOverEighteen(boolean overEighteen) {
        isOverEighteen = overEighteen;
    }
    public boolean isOverEighteen() {
        return isOverEighteen;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public Category getCategory() {
        return category;
    }

    public void setProtagonists(String protagonists) {
        this.protagonists = protagonists;
    }
    public String getProtagonists() {
        return protagonists;
    }

    public ArrayList<Content> getAlike() {
        return alike;
    }

    public void addRating(Rating r){
        ratings.add(r);
    }
    public  ArrayList<Rating> getRatings(){
        return ratings;
    }

    /**
     * μέθοδος που υπολογίζει τον μέσο όρο
     * <p>
     * των βαθμολογιών των αξιολογίσεων για το συγκεκριμένο περιεχώμενο
     * @return  ο μέσος όρος
     */
    public double getAverageRating(){
        int sum = 0;
        double average;
        for(Rating r : ratings){
            sum+= r.getRating();
        }
        if(ratings==null){
            average = -1;
        }
        else{
            average = sum / (double)ratings.size();
        }
        return  average;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return isOverEighteen == content.isOverEighteen && Objects.equals(title, content.title) && Objects.equals(description, content.description) && category == content.category && Objects.equals(protagonists, content.protagonists) && Objects.equals(ratings, content.ratings) && Objects.equals(alike, content.alike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, isOverEighteen, category, protagonists, ratings, alike);
    }
}
