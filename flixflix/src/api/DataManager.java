package api;

import java.io.*;
import java.util.ArrayList;


/**
 * Η συγκεκριμένη κλάση διαχειρίζεται τα δεδομένα της εφαρμογής
 * <p>
 * σημείωση: οι χρήστες (users) και το περιεχόμενο (content) αποθηκεύονται σε λίστες τύπου ArrayList και σε δυο διαφορετικά αρχεία
 * <p>
 * Όταν γίνεται μια οποιαδήποτε αλλαγή στα δεδομένα της εφαρμογής, στην ουσία παίρνουμε την εκάστοτε λίστα, κάνουμε την αλλαγή (π.χ. διαγραφή μίας σειράς
 * ή προσθήκη μιας αξιολόγησης) και εναποθηκεύουμε ολόκληρη τη λίστα στο αρχείο.
 */
public class DataManager {
    private static final String  CONTENT_FILE = "content.dat";
    private static final String  USERS_FILE = "users.dat";

    public static ArrayList<Content> contentList = new ArrayList<>(); //λίστα που περιέχει όλες τις ταινίες και τις σειρές

    public  static ArrayList<User> usersList = new ArrayList<>();// λίστα που αποθηκεύει τα credentials των χρηστών

    /**
     * Αυτή η μέθοδος φορτώνει στη λίστα @contentList ta δεδομένα απο το αρχείο content.dat
     */
    public static void loadContentsFromFile(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(CONTENT_FILE))){
            contentList = (ArrayList<Content>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Η μέθοδος αυτή αποθηκεύει το δοσμένο περιεχόμενο στη
     * λίστα με τα περιεχόμενα (@contentList)
     * @param content το περιεχόμενο που θέλω να αποθηκεύσω
     */
    public static void saveContentToFile(Content content){
        contentList.add(content);
        saveContentsToFile();
    }

    /**
     * Αυτή η μέθοδος σώζει ολόκληρη τη λίστα με τα περιεχόμενα (@contentList) στο αρχείο.
     */
    public static void saveContentsToFile(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CONTENT_FILE))){
            out.writeObject(contentList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Αυτή η μέθοδος προσθέτει ενα περιεχόμενο στη λίστα αγαπημένων του συγκεκριμένου συνδρομητή
     * @param subscriber ο συνδρομητής που θα προστεθεί το περιεχόμενο στη λίστα αγαπημένων του
     * @param content το περιεχόμενο που θα προστεθεί στη λίστα
     */
    public static void addToFavouritesList(Subscriber subscriber, Content content){
        if(!subscriber.getFavouritesList().contains(content)){
            subscriber.addToFavouriteList(content);
        }
        saveUsersToFile();
    }

    /**
     * Αυτή η μέθοδος διαγράφει ενα περιεχόμενο από τη λίστα αγαπημένων του συγκεκριμένου συνδρομητή
     * @param subscriber ο συνδρομητής που θα διαγραφεί το περιεχόμενο από τη λίστα αγαπημένων του
     * @param content το περιεχόμενο που θα διαγραφεί από τη λίστα
     */
    public static void deleteFromFavouritesList(Subscriber subscriber,Content content){
        subscriber.deleteFromFavouriteList(content);
        saveUsersToFile();
    }

    /**
     * Η μέθοδος αυτή διαγράφει το δοσμένο περιεχόμενο απο το σύστημα.
     * Επίσης, διαγράφεται απο οποιαδήποτε λίστα αγαπημένων οποιουδήποτε χρήστη υπάρχει
     * @param content το δοσμένο περιεχόμενο
     */
    public static void deleteContent(Content content){
        for(User u: usersList){
            if(u instanceof Subscriber){
                ((Subscriber) u).getFavouritesList().remove(content);
            }
        }
        contentList.remove(content);
        saveContentsToFile();
    }

    /**
     * Η μέθοδος αυτή διαγράφει τη δοσμένη αξιολόγηση απο το σύστημα.
     * @param subscriber ο συνδρομητής που θέλει να διαγράψει την αξιολόγηση του
     * @param selectedContent το περιεχόμενο που θέλουμε να διαγράψουμε την αξιολόγηση
     */
    public static void deleteRating(Subscriber subscriber, Content selectedContent) {
        selectedContent.getRatings().removeIf(r -> r.getAuthor().equals(subscriber));
        saveContentsToFile();
    }

    /**
     * Αυτή η μέθοδος φορτώνει στη λίστα @usersList ta δεδομένα απο το αρχείο users.dat
     */
    public static void loadUsersFromFile(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_FILE))){
            usersList = (ArrayList<User>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Αυτή η μέθοδος προσθέτει τον δοσμένο χρήστη στη λίστα @usersList και την αποθηκεύει στο αρχείο
     * @param user ο χρήστης που θα προστεθεί στο αρχείο
     */
    public static void saveUserToFile(User user){
        usersList.add(user);
        saveUsersToFile();
    }

    /**
     * Αυτή η μέθοδος σώζει ολόκληρη τη λίστα με τους χρήστες (@usersList) στο αρχείο.
     */
    public static void saveUsersToFile(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_FILE))){//δεν ξερω αν πρεπει να κανει append
            out.writeObject(usersList);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void initUsersData(){
        Admin adm1 = new Admin("admin1", "password1");
        Admin adm2 = new Admin("admin2", "password2");
        Subscriber sub1 = new Subscriber("name1","surname1","user1","password1");
        Subscriber sub2 = new Subscriber("name2","surname2","user2","password2");
        Subscriber sub3 = new Subscriber("Vaggelis", "Papaioannou", "vag1","password1");
        Subscriber sub4 = new Subscriber("Giannis", "Moschou", "imoschou", "1234");


        usersList.add(adm1);
        usersList.add(adm2);
        usersList.add(sub1);
        usersList.add(sub2);
        usersList.add(sub3);
        usersList.add(sub4);
        saveUsersToFile();

    }

    public static void initContent(){
        Movie a = new Movie();
        a.setTitle("Harry potter and the philosophers stone");
        a.setDescription("A movie about magic");
        a.setOverEighteen(false);
        a.setCategory(Category.SCIFI);
        a.setProtagonists("Daniel Radcliffe\n" +
                "Rupert Grint\n" +
                "Emma Watson");
        a.setYear(2001);
        a.setDurationInMinutes(152);


        Movie b = new Movie();
        b.setTitle("Shutter Island");
        b.setDescription("a psychological thriller about a man investigating a murder on an asylum");
        b.setOverEighteen(true);
        b.setCategory(Category.DRAMA);
        b.setProtagonists(
                "Leonardo DiCaprio," +
                " Mark Ruffalo");
        b.setYear(2010);
        b.setDurationInMinutes(139);

        saveContentToFile(a);
        saveContentToFile(b);
    }
}
