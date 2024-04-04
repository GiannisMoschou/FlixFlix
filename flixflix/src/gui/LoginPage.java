package gui;
import api.UserAuthentication;

import javax.swing.*;


public class LoginPage extends JFrame {
    private final ImageIcon icon = new ImageIcon("netflix.jpg");
    private JPanel panel1;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel registerLabel;

    public LoginPage(){
        makeFrame();
        buttons();
    }

    private void makeFrame(){
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        this.setContentPane(panel1);
        this.setTitle("Welcome to FlixFlix");
        this.setSize(400,450);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buttons(){
        registerButton.addActionListener(e -> new RegisterForm());
        loginButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if(UserAuthentication.authentication(username,password)){
                if(UserAuthentication.isAdmin()){
                    dispose();
                    new AdminPage(username);
                }
                else {
                    dispose();
                    new SubscriberPage(UserAuthentication.getSubscriber(username,password));
                }
            }
            if(!UserAuthentication.authentication(username,password)){
                JOptionPane.showMessageDialog(null, "Login Unsuccessful \n Try Again", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}