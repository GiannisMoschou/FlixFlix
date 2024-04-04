package gui;

import api.*;

import javax.swing.*;

import java.util.ArrayList;

public class SeriesListing extends JFrame {
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JPanel SeriesInformationPanel;
    private JTextField titleTextField;
    private JTextField descriptionTextField;
    private JTextField protagonistsTextField;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JRadioButton HORRORRadioButton;
    private JRadioButton SCIFIRadioButton;
    private JRadioButton ACTIONRadioButton;
    private JRadioButton COMEDYRadioButton;
    private JRadioButton DRAMARadioButton;
    private JList seasonsList;
    private JList episodesList;
    private JButton addSeasonButton;
    private JButton deleteSeasonButton;
    private JButton addEpisodeButton;
    private JButton deleteEpisodeButton;
    private JButton backButton;
    private JButton saveButton;
    private JButton editSeasonButton;
    private JButton editEpisodeButton;
    private JPanel alikePanel;
    private JList alikeList;
    private JList myAlikeList;
    private JButton addToSimilarButton;
    private JButton deleteFromSimilarButton;
    private JButton updateButton;
    private final ButtonGroup categoryGroup = new ButtonGroup();
    private final ButtonGroup isOver18Group = new ButtonGroup();
    private Series series;
    private Content selectedContent;
    private Content selectedContentToBeDeleted;
    private ArrayList<Content> alikeToBeSaved = new ArrayList<>();
    private Season selectedSeason;
    private Episode selectedEpisode;
    private DefaultListModel<Content> alikeListModel;
    private DefaultListModel<Content> myAlikeListModel;

    /**
     * Ο κατασκευαστής αυτός χρησιμοποιείται όταν θέλω να
     * προσθέσω μια καινούρια σειρά στο σύστημα.
     */
    public SeriesListing(){
        series = new Series();
        makeFrame();
        buttons();
        setAlike();
        saveButton.addActionListener(e -> {
            if(titleTextField.getText().equals("") || descriptionTextField.getText().equals("") || protagonistsTextField.getText().equals("")  || isOver18Group.getSelection()==null|| categoryGroup.getSelection() == null){
                JOptionPane.showMessageDialog(null,"you have to fill all before saving","",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                series.setTitle(titleTextField.getText());
                series.setProtagonists(protagonistsTextField.getText());
                series.setDescription(descriptionTextField.getText());
                if(yesRadioButton.isSelected()){
                    series.setOverEighteen(false);
                }
                else if(noRadioButton.isSelected()){
                    series.setOverEighteen(true);
                }

                if(HORRORRadioButton.isSelected()){
                    series.setCategory(Category.HORROR);
                }
                else if(SCIFIRadioButton.isSelected()){
                    series.setCategory(Category.SCIFI);
                }
                else if(DRAMARadioButton.isSelected()){
                    series.setCategory(Category.DRAMA);
                }
                else if(COMEDYRadioButton.isSelected()){
                    series.setCategory(Category.COMEDY);
                }
                else{
                    series.setCategory(Category.ACTION);
                }
                series.getAlike().addAll(alikeToBeSaved);
                DataManager.saveContentToFile(series);
                dispose();
            }
        });

    }

    /**
     * Ο κατασκευαστής αυτός καλείται όταν θέλω να επεξεργαστώ μια σειρά.
     * @param series η σειρά που θέλω να επεξεργαστώ
     */
    public SeriesListing(Series series){
        this.series = series;
        titleTextField.setText(series.getTitle());
        descriptionTextField.setText(series.getDescription());
        protagonistsTextField.setText(series.getProtagonists());
        Category category = series.getCategory();
        if(series.isOverEighteen()){
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
        makeFrame();
        setSeasonsList();
        alikeToBeSaved.addAll(series.getAlike());
        setAlike();
        setMyAlikeList();
        buttons();
        saveButton.addActionListener(e -> {
            if(titleTextField.getText().equals("") || descriptionTextField.getText().equals("") || protagonistsTextField.getText().equals("")  || isOver18Group.getSelection()==null|| categoryGroup.getSelection() == null){
                JOptionPane.showMessageDialog(null,"you have to fill all before saving","",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                series.setTitle(titleTextField.getText());
                series.setProtagonists(protagonistsTextField.getText());
                series.setDescription(descriptionTextField.getText());
                if(yesRadioButton.isSelected()){
                    series.setOverEighteen(false);
                }
                else if(noRadioButton.isSelected()){
                    series.setOverEighteen(true);
                }

                if(HORRORRadioButton.isSelected()){
                    series.setCategory(Category.HORROR);
                }
                else if(SCIFIRadioButton.isSelected()){
                    series.setCategory(Category.SCIFI);
                }
                else if(DRAMARadioButton.isSelected()){
                    series.setCategory(Category.DRAMA);
                }
                else if(COMEDYRadioButton.isSelected()){
                    series.setCategory(Category.COMEDY);
                }
                else{
                    series.setCategory(Category.ACTION);
                }
                series.getAlike().addAll(alikeToBeSaved);
                DataManager.saveContentsToFile();
                dispose();
            }

        });
    }

    private void setSeasonsList() {
        DefaultListModel<Season> seasonsListModel = new DefaultListModel();
        for(Season s : series.getSeasons()){
            seasonsListModel.addElement(s);
        }
        seasonsList.setModel(seasonsListModel);
        seasonsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        seasonsList.addListSelectionListener(e -> {
            selectedSeason = (Season) seasonsList.getSelectedValue();
            if(selectedSeason!=null){
                setEpisodesList(selectedSeason);
            }
        });
    }

    private void setAlike(){
        alikeListModel = new DefaultListModel();
        for(Content content:DataManager.contentList){
            if(content instanceof Series && content!=series){
                alikeListModel.addElement(content);
            }
        }
        alikeList.setModel(alikeListModel);
        alikeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alikeList.addListSelectionListener(e -> selectedContent = (Content) alikeList.getSelectedValue());
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

    private void setEpisodesList(Season season) {
        DefaultListModel<Episode> episodeListModel = new DefaultListModel<>();
        for(Episode episode:season.getEpisodes()){
            episodeListModel.addElement(episode);
        }
        episodesList.setModel(episodeListModel);
        episodesList.addListSelectionListener(e -> selectedEpisode = (Episode) episodesList.getSelectedValue());
    }

    private void buttons(){
        backButton.addActionListener(e -> dispose());

        addSeasonButton.addActionListener(e -> {
            new SeasonListing(series);
        });

        editSeasonButton.addActionListener(e -> {
            if(selectedSeason!=null) {
                new SeasonListing(series,selectedSeason);
            }
            });

        deleteSeasonButton.addActionListener(e -> {
            if(selectedSeason!=null){
                series.deleteSeason(selectedSeason);
            }
        });

        addEpisodeButton.addActionListener(e -> {
            if(selectedSeason!=null){
                new EpisodeListing(selectedSeason);
            }
        });

        editEpisodeButton.addActionListener(e -> {
            if(selectedSeason!=null && selectedEpisode !=null){
                new EpisodeListing(selectedEpisode);
            }
        });

        deleteEpisodeButton.addActionListener(e -> {
            if(selectedEpisode!=null && selectedSeason!=null){
                selectedSeason.deleteEpisode(selectedEpisode);
                setSeasonsList();
            }
        });

        addToSimilarButton.addActionListener(e -> {
            if(selectedContent!=null && !alikeToBeSaved.contains(selectedContent)){
                alikeToBeSaved.add(selectedContent);
                setMyAlikeList();
            }
        });
        deleteFromSimilarButton.addActionListener(e -> {
            if(selectedContentToBeDeleted!=null){
                alikeToBeSaved.remove(selectedContentToBeDeleted);
                setMyAlikeList();
            }
        });
        updateButton.addActionListener(e -> setSeasonsList());

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
        this.setSize(900,700);
        this.setResizable(false);
        this.setTitle("Series Listing");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}