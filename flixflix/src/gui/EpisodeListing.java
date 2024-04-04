package gui;

import api.DataManager;
import api.Episode;
import api.Season;

import javax.swing.*;
import java.util.Objects;

public class EpisodeListing extends JFrame{
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JButton saveButton;
    private JTextField titleTextField;
    private JTextField durationTextField;
    private JButton backButton;
    private Episode episode;
    private Season season;

    /**
     * Ο κατασκευαστής χρησιμοποιείται για να
     * προσθέσουμε ένα καινούριο επεισόδιο
     * στην επιλεγμένη σεζόν
     * @param season η σεζόν στην οποία θέλουμε να προσθέσουμε το επεισόδιο
     */
    public EpisodeListing(Season season){
        this.season = season;
        makeFrame();
        saveButton.addActionListener(e -> {
            if(!Objects.equals(titleTextField.getText(), " ") && durationTextField.getText().matches("\\d+")){
                episode = new Episode(titleTextField.getText(),Integer.parseInt(durationTextField.getText()));
                season.addEpisode(episode);
                dispose();
                JOptionPane.showMessageDialog(null,"season successfully added to series, press update to see");
            }
            else {
                JOptionPane.showMessageDialog(null,"Wrong input");
            }
        });
        backButton.addActionListener(e -> dispose());
    }

    /**
     * Ο κατασκευαστής αυτός χρησιμοποιείται όταν θέλω να επεξεργαστώ ένα επεισόδιο
     * @param episode το επισόδιο που θέλω να επεξεργαστώ
     */
    public EpisodeListing(Episode episode){
        makeFrame();
        titleTextField.setText(episode.getTitle());
        durationTextField.setText(String.valueOf(episode.getDuration()));
        saveButton.addActionListener(e -> {
            episode.setTitle(titleTextField.getText());
            episode.setDuration(Integer.parseInt(durationTextField.getText()));
            DataManager.saveContentsToFile();
            dispose();
        });
        backButton.addActionListener(e -> dispose());
    }

    private void makeFrame() {
        this.setContentPane(panel1);
        this.setSize(350,300);
        this.setResizable(false);
        this.setTitle("Episode Listing");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
