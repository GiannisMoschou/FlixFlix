package gui;

import api.Category;
import api.Content;
import api.DataManager;
import api.Movie;

import javax.swing.*;
import java.util.ArrayList;

public class MovieListing extends JFrame {
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JTextField titleTextField;
    private JTextField descriptionTextField;
    private JTextField protagonistsTextField;
    private JTextField yearTextField;
    private JTextField durationTextField;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JRadioButton HORRORRadioButton;
    private JRadioButton DRAMARadioButton;
    private JRadioButton ACTIONRadioButton;
    private JRadioButton SCIFIRadioButton;
    private JRadioButton COMEDYRadioButton;
    private JButton saveButton;
    private JButton backButton;
    private JList alikeList;
    private JButton addToAlikeButton;
    private JList myAlikeList;
    private JButton deleteFromAlikeButton;
    private DefaultListModel<Content> alikeListModel;
    private DefaultListModel<Content> myAlikeListModel;
    private final ButtonGroup categoryGroup = new ButtonGroup();
    private final ButtonGroup isOver18Group = new ButtonGroup();
    private ArrayList<Content> alikeToBeSaved = new ArrayList<>();
    private Content selectedContent;
    private Content selectedContentToBeDeleted;

    /**
     * Ο συγκεκριμένος κατασκευαστής χρησιμοποιείται
     * για να εισάγω μια καινούρια ταινία στο σύστημα
     */
    public MovieListing(){
        makeFrame();
        setAlike();
        buttons();
    }

    /**
     * Ο κατασκευαστής αυτός χρησιμοποιείται όταν
     * <p>
     * θέλω να επεξεργαστώ μια ήδη υπάρχουσα ταινία
     * @param movie η δοσμένη ταινία που θέλω να επεξεργαστώ
     */
    public MovieListing(Movie movie){
        makeFrame();
        setEditPage(movie);
    }

    private void makeFrame(){
        categoryGroup.add(HORRORRadioButton);
        categoryGroup.add(DRAMARadioButton);
        categoryGroup.add(ACTIONRadioButton);
        categoryGroup.add(SCIFIRadioButton);
        categoryGroup.add(COMEDYRadioButton);
        isOver18Group.add(yesRadioButton);
        isOver18Group.add(noRadioButton);

        this.setContentPane(panel1);
        this.setSize(800,700);
        this.setResizable(true);
        this.setTitle("Movie Listing");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setAlike(){
        alikeListModel = new DefaultListModel();
        for(Content content:DataManager.contentList){
            if(content instanceof Movie){
                alikeListModel.addElement(content);
            }
        }
        alikeList.setModel(alikeListModel);
        alikeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alikeList.addListSelectionListener(e -> selectedContent = (Content)alikeList.getSelectedValue());
    }

    private void setMyAlikeList(){
        myAlikeListModel = new DefaultListModel<>();
        for(Content c : alikeToBeSaved){
            myAlikeListModel.addElement(c);
        }
        myAlikeList.setModel(myAlikeListModel);
        myAlikeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myAlikeList.addListSelectionListener(e -> selectedContentToBeDeleted = (Content) myAlikeList.getSelectedValue());
    }

    private void buttons(){
        deleteFromAlikeButton.addActionListener(e -> {
            if(selectedContentToBeDeleted!=null){
                alikeToBeSaved.remove(selectedContentToBeDeleted);
                setMyAlikeList();
            }
        });
        addToAlikeButton.addActionListener(e -> {
            if(selectedContent!=null && !alikeToBeSaved.contains(selectedContent)){
                alikeToBeSaved.add(selectedContent);
                setMyAlikeList();
            }
        });
        saveButton.addActionListener(e -> {
            if(titleTextField.getText().equals("") || descriptionTextField.getText().equals("") || protagonistsTextField.getText().equals("") || yearTextField.getText().equals("") || durationTextField.getText().equals("") || isOver18Group.getSelection()==null|| categoryGroup.getSelection() == null){
                JOptionPane.showMessageDialog(null,"you have to fill all before saving","",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(!yearTextField.getText().matches("\\d+") || !durationTextField.getText().matches("\\d+")){
                JOptionPane.showMessageDialog(null,"wrong input","",JOptionPane.WARNING_MESSAGE);
            }
            else {
                boolean flag = true;
                if(noRadioButton.isSelected()){
                    flag = false;
                }
                Content movie = new Movie(titleTextField.getText(),descriptionTextField.getText(),flag,
                        getCategory(),protagonistsTextField.getText(),Integer.parseInt(yearTextField.getText()),
                        Integer.parseInt(durationTextField.getText()));

                movie.getAlike().addAll(alikeToBeSaved);
                DataManager.saveContentToFile(movie);
                JOptionPane.showMessageDialog(null,"Movie saved successfully","Saved",JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        backButton.addActionListener(e -> dispose());
    }

    private void setEditPage(Movie movie){
        alikeListModel = new DefaultListModel();
        for(Content content:DataManager.contentList){
            if(content instanceof Movie && content!=movie){
                alikeListModel.addElement(content);
            }
        }
        alikeList.setModel(alikeListModel);
        alikeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alikeList.addListSelectionListener(e -> selectedContent = (Content)alikeList.getSelectedValue());

        alikeToBeSaved.addAll(movie.getAlike());
        setMyAlikeList();
        deleteFromAlikeButton.addActionListener(e -> {
            if(selectedContentToBeDeleted!=null){
                alikeToBeSaved.remove(selectedContentToBeDeleted);
                setMyAlikeList();
            }
        });
        addToAlikeButton.addActionListener(e -> {
            if(selectedContent!=null && !alikeToBeSaved.contains(selectedContent)){
                alikeToBeSaved.add(selectedContent);
                setMyAlikeList();
            }
        });

        titleTextField.setText(movie.getTitle());
        descriptionTextField.setText(movie.getDescription());
        yearTextField.setText(String.valueOf(movie.getYear()));
        durationTextField.setText(String.valueOf(movie.getDurationInMinutes()));
        protagonistsTextField.setText(movie.getProtagonists());
        Category category = movie.getCategory();
        if(movie.isOverEighteen()){
            noRadioButton.setSelected(true);
        }
        else {
            yesRadioButton.setSelected(true);
        }
        if(category.equals(Category.HORROR)){
            HORRORRadioButton.setSelected(true);
        }
        else if(category.equals(Category.ACTION)){
            ACTIONRadioButton.setSelected(true);
        }
        else if(category.equals(Category.COMEDY)){
            COMEDYRadioButton.setSelected(true);
        }
        else if(category.equals(Category.DRAMA)){
            DRAMARadioButton.setSelected(true);
        }
        else {
            SCIFIRadioButton.setSelected(true);
        }
        saveButton.addActionListener(e -> {
            movie.setTitle(titleTextField.getText());
            movie.setYear(Integer.parseInt(yearTextField.getText()));
            movie.setDurationInMinutes(Integer.parseInt(durationTextField.getText()));
            movie.setDescription(descriptionTextField.getText());
            movie.setProtagonists(protagonistsTextField.getText());
            movie.setCategory(getCategory());
            movie.setOverEighteen(getSuitability());
            movie.getAlike().clear();
            movie.getAlike().addAll(alikeToBeSaved);
            DataManager.saveContentsToFile();
            dispose();
        });
        backButton.addActionListener(e -> dispose());
    }

    private Category getCategory(){
        Category c;
        if (HORRORRadioButton.isSelected()) {
            c = Category.HORROR;
        }
        else if(ACTIONRadioButton.isSelected()){
            c = Category.ACTION;
        }
        else if(DRAMARadioButton.isSelected()){
            c = Category.DRAMA;
        }
        else if(COMEDYRadioButton.isSelected()){
            c = Category.COMEDY;
        }
        else {
            c = Category.SCIFI;
        }
        return c;
    }

    private boolean getSuitability(){
        if(yesRadioButton.isSelected()){
            return false;
        }
        else {
            return true;
        }

    }
}