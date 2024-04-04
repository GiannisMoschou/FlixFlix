package gui;

import api.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminPage extends JFrame {
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    public String  admin;
    private JButton searchButton;
    private JPanel panel1;
    private JButton addMovieButton;
    private JButton logoutButton;
    private JButton addSeriesButton;
    private JList searchResultsList;
    private JPanel SearchPanel;
    private JTextField titleTextField;
    private JTextField protagonistTextField;
    private JTextField minRatingTextField;
    private JLabel protagonistLabel;
    private JRadioButton movieRadioButton;
    private JRadioButton seriesRadioButton;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JRadioButton HORRORRadioButton;
    private JRadioButton COMEDYRadioButton;
    private JRadioButton ACTIONRadioButton;
    private JRadioButton SCIFIRadioButton;
    private JRadioButton DRAMARadioButton;
    private JTextArea showTextArea;
    private JButton editButton;
    private JButton deleteButton;
    private JButton clearButton;
    private final ButtonGroup typeGroup = new ButtonGroup();
    private final ButtonGroup categoryGroup = new ButtonGroup();
    private final ButtonGroup isOver18Group = new ButtonGroup();
    private Content selectedContent;
    private DefaultListModel defaultListModel;
    private ArrayList<Content> results = new ArrayList<>();

    public AdminPage(String admin){
        this.admin = admin;
        makeFrame();
        buttons();
        setResultList();
    }

    private void makeFrame(){
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
        this.setTitle(admin);
        this.setSize(1100,700);
        this.setResizable(true);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buttons(){

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
            DataManager.saveContentsToFile();
        });

        addMovieButton.addActionListener(e -> new MovieListing());

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
            else if(SCIFIRadioButton.isSelected()){
                category = Category.SCIFI;
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

        addSeriesButton.addActionListener(e -> new SeriesListing());

        clearButton.addActionListener(e -> {
            typeGroup.clearSelection();
            categoryGroup.clearSelection();
            isOver18Group.clearSelection();
            titleTextField.setText("");
            protagonistTextField.setText("");
            minRatingTextField.setText("");
            searchResultsList.removeAll();
            defaultListModel.removeAllElements();
            showTextArea.setText("");
        });
        editButton.addActionListener(e -> {
            if(selectedContent instanceof Movie){
                new MovieListing((Movie) selectedContent);
            }
            else if(selectedContent instanceof Series){
                new SeriesListing((Series) selectedContent);
            }
        });
        deleteButton.addActionListener(e -> {
            if(selectedContent != null){
                DataManager.deleteContent(selectedContent);
                JOptionPane.showMessageDialog(null,selectedContent.getTitle()+" is successfully deleted","",JOptionPane.INFORMATION_MESSAGE);
                defaultListModel.removeAllElements();
                showTextArea.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                DataManager.saveContentsToFile();
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
            selectedContent = (Content) searchResultsList.getSelectedValue();
            if(selectedContent instanceof Movie){
                showTextArea.setText(Show.showMovie((Movie) selectedContent));
            }
            else if(selectedContent instanceof Series){
                showTextArea.setText(Show.showSeries((Series) selectedContent));
            }
        });
    }
}
