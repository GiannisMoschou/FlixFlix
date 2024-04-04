package gui;

import api.Content;
import api.DataManager;
import api.Rating;
import api.Subscriber;

import javax.swing.*;

public class RatingPage extends JFrame{
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JTextArea ratingDocumentTextArea;
    private JButton saveButton;
    private JTextField numRatingTextField;
    private JButton backButton;
    private Rating rating;
    private Subscriber subscriber;
    private Content content;

    /**
     * Αυτός ο κατασκευαστής χρησιμοποιείται όταν θέλω να εισάγω ενα καινούριο rating στο δοσμένο περιεχόμενο
     * @param subscriber ο συνδρομητής που γράφει το rating
     * @param selectedContent το περιεχόμενο που θα μπει το rating
     */
    public RatingPage(Subscriber subscriber, Content selectedContent){
        this.subscriber = subscriber;
        content = selectedContent;
        rating = new Rating();
        makeFrame();
        buttons();
    }

    /**
     * Ο κατασκευαστής αυτός καλείται όταν θέλω να κάνω edit ενα ήδη υπάρχων rating
     */
    public RatingPage(Subscriber subscriber,Content selectedContent, Rating rating){
        this.subscriber = subscriber;
        content = selectedContent;
        this.rating = rating;
        makeFrame();
        ratingDocumentTextArea.setText(rating.getRatingDocument());
        numRatingTextField.setText(String.valueOf(rating.getRating()));
        backButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if(ratingDocumentTextArea.getText()==null || numRatingTextField.getText()==null){
                JOptionPane.showMessageDialog(null,"Wrong input","Error",JOptionPane.WARNING_MESSAGE);
            }
            else if(Double.parseDouble(numRatingTextField.getText())<0 || Double.parseDouble(numRatingTextField.getText())>5){
                JOptionPane.showMessageDialog(null,"the rating has to be between 1 and 5","Error",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                rating.setRatingDocument(ratingDocumentTextArea.getText());
                rating.setRating(Double.parseDouble(numRatingTextField.getText()));
                rating.setAuthor(subscriber);
                DataManager.saveContentsToFile();
                dispose();
            }
        });
    }

    private void makeFrame() {
        this.setContentPane(panel1);
        this.setTitle(content+" rating");
        this.setSize(300, 400);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buttons(){
        backButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if(ratingDocumentTextArea.getText()==null || numRatingTextField.getText()==null){
                JOptionPane.showMessageDialog(null,"Wrong input","Error",JOptionPane.WARNING_MESSAGE);
            }
            else if(Double.parseDouble(numRatingTextField.getText())<0 || Double.parseDouble(numRatingTextField.getText())>5){
                JOptionPane.showMessageDialog(null,"the rating has to be between 1 and 5","Error",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                rating.setRatingDocument(ratingDocumentTextArea.getText());
                rating.setRating(Double.parseDouble(numRatingTextField.getText()));
                rating.setAuthor(subscriber);
                content.addRating(rating);
                DataManager.saveContentsToFile();
                dispose();
            }
        });
    }
}
