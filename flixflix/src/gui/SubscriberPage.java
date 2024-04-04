package gui;

import api.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SubscriberPage extends JFrame {
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private Subscriber subscriber;
    private JPanel panel1;
    private JPanel searchPanel;
    private JButton logoutButton;
    private JTextField titleTextField;
    private JTextField protagonistTextField;
    private JTextField minRatingTextField;
    private JRadioButton movieRadioButton;
    private JRadioButton seriesRadioButton;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JRadioButton SCIFIRadioButton;
    private JRadioButton HORRORRadioButton;
    private JRadioButton COMEDYRadioButton;
    private JRadioButton ACTIONRadioButton;
    private JRadioButton DRAMARadioButton;
    private JButton searchButton;
    private JList searchResultsList;
    private JPanel showPanel;
    private JTextArea showTextArea;
    private JButton editButton;
    private JButton deleteRatingButton;
    private JButton addNewRatingButton;
    private JList favouritesList;
    private JButton addToFavouritesButton;
    private JButton removeFromFavouritesButton;
    private JButton clearButton;
    private DefaultListModel<Content> defaultListModel;
    private DefaultListModel<Content> favouritesListModel;
    private ArrayList<Content> results = new ArrayList<>();
    private Content selectedContentFromSearch;
    private Content selectedContentFromFavouritesList;
    private Content selectedContent;
    private final ButtonGroup typeGroup = new ButtonGroup();
    private final ButtonGroup categoryGroup = new ButtonGroup();
    private final ButtonGroup isOver18Group = new ButtonGroup();

    public SubscriberPage(Subscriber subscriber){
        this.subscriber = subscriber;
        makeFrame();
        buttons();
        setFavouritesList();
    }

    private void setFavouritesList(){
        favouritesListModel = new DefaultListModel<>();
        for(Content content : subscriber.getFavouritesList()){
            favouritesListModel.addElement(content);
        }
        favouritesList.setModel(favouritesListModel);
        favouritesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favouritesList.addListSelectionListener(e -> {
            selectedContentFromFavouritesList = (Content) favouritesList.getSelectedValue();
            selectedContent = selectedContentFromFavouritesList;
            if(selectedContentFromFavouritesList instanceof Movie){
                showTextArea.setText(Show.showMovie((Movie) selectedContentFromFavouritesList));
            }
            else if(selectedContentFromFavouritesList instanceof Series){
                showTextArea.setText(Show.showSeries((Series) selectedContentFromFavouritesList));
            }
        });

    }

    private void setResultList(){
        defaultListModel = new DefaultListModel();
        for(Content content:results){
            defaultListModel.addElement(content);
        }
        searchResultsList.setModel(defaultListModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.addListSelectionListener(e -> {
            selectedContentFromSearch = (Content) searchResultsList.getSelectedValue();
            selectedContent = selectedContentFromSearch;
            if(selectedContentFromSearch instanceof Movie){
                showTextArea.setText(Show.showMovie((Movie) selectedContentFromSearch));
            }
            else if(selectedContentFromSearch instanceof Series){
                showTextArea.setText(Show.showSeries((Series) selectedContentFromSearch));
            }
        });
    }

    private void makeFrame() {
        typeGroup.add(movieRadioButton);
        typeGroup.add(seriesRadioButton);
        categoryGroup.add(HORRORRadioButton);
        categoryGroup.add(COMEDYRadioButton);
        categoryGroup.add(DRAMARadioButton);
        categoryGroup.add(SCIFIRadioButton);
        categoryGroup.add(ACTIONRadioButton);
        isOver18Group.add(yesRadioButton);
        isOver18Group.add(noRadioButton);
        this.setContentPane(panel1);
        this.setTitle(subscriber.getUsername());
        this.setSize(1100, 700);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buttons(){
        clearButton.addActionListener(e -> {
            if(defaultListModel!=null){
                typeGroup.clearSelection();
                categoryGroup.clearSelection();
                isOver18Group.clearSelection();
                titleTextField.setText("");
                protagonistTextField.setText("");
                minRatingTextField.setText("");
                searchResultsList.removeAll();
                defaultListModel.removeAllElements();
                showTextArea.setText("");
            }
        });

        addToFavouritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedContentFromSearch!=null){
                    if(subscriber.getFavouritesList().contains(selectedContentFromSearch) ){
                        JOptionPane.showMessageDialog(null,"You already have it in your favourites list","",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        DataManager.addToFavouritesList(subscriber,selectedContentFromSearch);
                        setFavouritesList();
                    }
                }
            }
        });
        removeFromFavouritesButton.addActionListener(e -> {
            if(selectedContentFromFavouritesList!=null){
                DataManager.deleteFromFavouritesList(subscriber,selectedContentFromFavouritesList);
                setFavouritesList();
            }
        });

        logoutButton.addActionListener(e -> {
            DataManager.saveContentsToFile();
            dispose();
            new LoginPage();
        });

        searchButton.addActionListener(e -> {
            results = new ArrayList<>();
            String title = titleTextField.getText();
            String type = "";
            Category category = null;
            String protagonist = protagonistTextField.getText();
            String isOverEighteen = "";
            String min = minRatingTextField.getText();
            int minRating;
            if(min.equals("")){
                minRating = -1;
            }
            else{
                minRating = Integer.parseInt(minRatingTextField.getText());
            }
            if(movieRadioButton.isSelected()){
                type = "movie";
            }
            else if(seriesRadioButton.isSelected()){
                type = "series";
            }
            if (HORRORRadioButton.isSelected()) {
                category = Category.HORROR;
            }
            else if(ACTIONRadioButton.isSelected()){
                category = Category.ACTION;
            }
            else if(DRAMARadioButton.isSelected()){
                category = Category.DRAMA;
            }
            else if(COMEDYRadioButton.isSelected()){
                category = Category.COMEDY;
            }
            else if(ACTIONRadioButton.isSelected()){
                category = Category.ACTION;
            }

            if(noRadioButton.isSelected()){
                isOverEighteen = "yes";
            }
            else if(yesRadioButton.isSelected()){
                isOverEighteen = "no";
            }
            results = Search.search(title,type,category,protagonist,isOverEighteen,minRating);
            if(results.isEmpty()){
                JOptionPane.showMessageDialog(null,"No matching results","",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                setResultList();
            }
        });

        addNewRatingButton.addActionListener(e -> {

            if(hasRating()){
                JOptionPane.showMessageDialog(null,"you already have submitted a rating you can't add more","you have a rating",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                if(selectedContent != null){
                    new RatingPage(subscriber,selectedContent);
                    searchResultsList.clearSelection();
                }
            }
        });

        editButton.addActionListener(e -> {
            if(hasRating()){
                if(selectedContent!=null){
                    for(Rating r : selectedContent.getRatings()){
                        if(r.getAuthor().equals(subscriber)){
                            new RatingPage(subscriber,selectedContent,r);
                            break;
                        }
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"You don't have a rating","",JOptionPane.INFORMATION_MESSAGE);
            }

        });

        deleteRatingButton.addActionListener(e -> {
            if(hasRating()){
                DataManager.deleteRating(subscriber,selectedContent);
            }
            else {
                JOptionPane.showMessageDialog(null,"You don't have a rating","",JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private boolean hasRating(){
        boolean hasRating = false;
        if(selectedContent!=null){
            for(Rating r : selectedContent.getRatings()){
                System.out.println(r.getAuthor());
                System.out.println(subscriber);
                if(r.getAuthor().equals(subscriber)){
                    hasRating = true;
                    break;
                }
            }
        }
        return hasRating;
    }
}
