import api.DataManager;
import gui.LoginPage;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {
    public static void main(String[] args) {
//     αυτό το μπλοκ κώδικα θα τρέξει μόνο μια φορά για να αρχικοποιήσει τους αρχικούς χρήστες στο αρχείο
//       DataManager.initUsersData();
//       DataManager.initContent();

       DataManager.loadUsersFromFile();
       DataManager.loadContentsFromFile();
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new LoginPage();
    }
}