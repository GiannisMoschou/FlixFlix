package api;

import java.io.Serializable;

/**
 * Αυτή η κλάση αναπαριστά έναν διαχειριστή
 */
public class Admin extends User implements Serializable {

    public Admin(String username,String password) {
        super(username,password);
    }

    public Admin() {
    }


    @Override
    public String toString() {
        return getUsername();
    }


}