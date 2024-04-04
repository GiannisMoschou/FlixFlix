package gui;

import api.DataManager;
import api.Season;
import api.Series;

import javax.swing.*;

public class SeasonListing extends JFrame{
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JTextField seasonNumberTextField;
    private JButton saveButton;
    private JTextField yearTextField;
    private JButton backButton;
    private Series series;
    private Season season;

    public SeasonListing(Series series , Season season){
        System.out.println(series.getSeasons());
        this.series = series;
        this.season = season;
        makeFrame();
        seasonNumberTextField.setText(String.valueOf(season.getSeasonNumber()));
        yearTextField.setText(String.valueOf(season.getYear()));
        saveButton.addActionListener(e -> {
            season.setSeasonNumber(Integer.parseInt(seasonNumberTextField.getText()));
            season.setYear(Integer.parseInt(yearTextField.getText()));
            DataManager.saveContentsToFile();
            dispose();
        });
        backButton.addActionListener(e -> dispose());
    }

    public SeasonListing(Series series){
        System.out.println(series.getSeasons());
        this.series = series;
        makeFrame();
        saveButton.addActionListener(e -> {
            if(seasonNumberTextField.getText().matches("\\d+")&& yearTextField.getText().matches("\\d+")){
                season = new Season(Integer.parseInt(seasonNumberTextField.getText()),Integer.parseInt(yearTextField.getText()));
                series.addSeason(season);
                DataManager.saveContentsToFile();
                dispose();
                JOptionPane.showMessageDialog(null,"season successfully added to series, press update to see");
            }
        });
        backButton.addActionListener(e -> dispose());
    }

    private void makeFrame(){
        this.setContentPane(panel1);
        this.setSize(350,300);
        this.setResizable(false);
        this.setTitle("Season Listing");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
