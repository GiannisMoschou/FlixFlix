package gui;

import api.*;

import javax.swing.*;
import java.util.Objects;

public class RegisterForm extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JButton createAccountButton;
    private JButton backButton;

    public RegisterForm(){
        makeFrame();
        buttons();
    }

    private void makeFrame(){
        ImageIcon icon = new ImageIcon("netflix.jpg");
        this.setContentPane(panel1);
        this.setTitle("FlixFlix");
        this.setSize(400,450);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buttons(){
        createAccountButton.addActionListener(e -> {

            String firstName = textField1.getText();
            String lastName = textField2.getText();
            String username = textField3.getText();
            String password = String.valueOf(passwordField1.getPassword());

            if(Objects.equals(firstName, "") && Objects.equals(lastName, "") && Objects.equals(username, "") && password.equals("")){
                JOptionPane.showMessageDialog(null, "Try again ", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(!UserAuthentication.isUsernameUnique(username)){
                JOptionPane.showMessageDialog(null, "Username already exists", "", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                User subscriber = new Subscriber(firstName,lastName,username,password);
                DataManager.saveUserToFile(subscriber);
                dispose();
                JOptionPane.showMessageDialog(null, "User created successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                new LoginPage();
            }
        });
        backButton.addActionListener(e -> dispose());
    }
}
