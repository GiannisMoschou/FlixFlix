package api;

public class UserAuthentication {
    private static boolean userAdmin;

    /**
     * Η αυθεντικοπίηση του χρήστη ώστε να γίνει η σύνδεση
     * @return true αν ο χρήστης υπάρχει στο σύστημα ή false αν δέν υπάρχει.
     */
    public static boolean authentication(String username,String password){
        boolean flag = false;
        for(User user: DataManager.usersList){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                flag = true;
                userAdmin = user instanceof Admin;
            }
        }
        return flag;
    }

    public static Subscriber getSubscriber(String username, String password){
        Subscriber s = new Subscriber();
        for(User user: DataManager.usersList){
            if(user instanceof Subscriber){
                if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                    s = (Subscriber) user;
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Τσεκάρει αν το username που θέλουμε είναι μοναδικό στο σύστημα.
     * @param username το username που τσεκάρει
     * @return true αν είναι μομναδικό ή false αν δέν είναι.
     */
    public  static boolean isUsernameUnique(String username){
        boolean isUnique = true;
        for(User s : DataManager.usersList){
            if(s.getUsername().equals(username)){
                isUnique =false;
                break;
            }
        }
        return isUnique;
    }
    public static boolean isAdmin(){
        return userAdmin;
    }
}
