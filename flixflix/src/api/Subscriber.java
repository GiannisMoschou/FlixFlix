package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Αυτή η κλάση αναπαριστά έναν συνδρομητή
 */

public class Subscriber extends User implements Serializable{
    private String name;
    private String surname;
    private ArrayList<Content> favouritesList;

    public Subscriber(String name, String surname,String username,String password) {
        super(username,password);
        this.name = name;
        this.surname = surname;
        favouritesList  = new ArrayList<>();
    }

    public Subscriber(){favouritesList  = new ArrayList<>();}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addToFavouriteList(Content c){
        favouritesList.add(c);
    }
    public void deleteFromFavouriteList(Content c){
        favouritesList.remove(c);
    }
    public ArrayList<Content> getFavouritesList(){
        return favouritesList;
    }

    @Override
    public String toString(){
        return getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(favouritesList, that.favouritesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, favouritesList);
    }
}
