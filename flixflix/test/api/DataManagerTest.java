package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * σημείωση: δεν χρησιμοποίησα τις μεθόδους της κλάσης DataManager
 * αλλα μόνο τη λογική τους καθώς δεν θέλω να πειράξω τα αρχεία στα τεστ
 */
public class DataManagerTest {
    @Test
    public void listsExist(){
        DataManager.loadUsersFromFile();
        DataManager.loadContentsFromFile();
        assertFalse(DataManager.usersList.isEmpty());
        assertFalse(DataManager.contentList.isEmpty());
    }

    @Test
    public void saveTest(){
        DataManager.loadContentsFromFile();
        Content m = DataManager.contentList.get(0);
        assertTrue(DataManager.contentList.contains(m));
    }

    @Test
    public void addToFavouritesList(){
        DataManager.loadContentsFromFile();
        DataManager.loadUsersFromFile();
        Content m = DataManager.contentList.get(0);
        Subscriber s = new Subscriber();
        for(User u :DataManager.usersList){
            if(u instanceof Subscriber){
                s = (Subscriber) u;
                break;
            }
        }
        s.addToFavouriteList(m);
        assertTrue(s.getFavouritesList().contains(m));
    }


    @Test
    public void deleteContentTest(){
        DataManager.loadContentsFromFile();
        DataManager.loadUsersFromFile();
        Content m = DataManager.contentList.get(0);
        Subscriber s = new Subscriber();
        for(User u :DataManager.usersList){
            if(u instanceof Subscriber){
                s = (Subscriber) u;
                break;
            }
        }
        DataManager.contentList.remove(m);
        assertFalse(DataManager.contentList.contains(m));
    }

    @Test
    public void deleteRatingTest(){
        DataManager.loadContentsFromFile();
        User sub = new Subscriber("aname","asurname","username1","1111");
        Content c = DataManager.contentList.get(0);
        Rating rating = new Rating();
        rating.setRatingDocument("test");
        rating.setRating(5);
        rating.setAuthor((Subscriber) sub);
        c.addRating(rating);
        assertTrue(c.getRatings().contains(rating));
        c.getRatings().removeIf(r -> r.getAuthor().equals(sub));
        assertFalse(c.getRatings().contains(rating));
    }
}
