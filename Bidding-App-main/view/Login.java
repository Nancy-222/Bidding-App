package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.AppController;
import models.User;

public class Login extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AppController controller;
    
    public Login(AppController controller){
        this.controller = controller;

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                User user = controller.checkUser(username, password);
                if(user != null){
                    controller.currentUser = user;
                    dispose();
                    new HomePage(controller);
                }else{
                    displayErrorMessage("Wrong username or password!");
                    clearFields();
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

}
