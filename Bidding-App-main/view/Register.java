package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.AppController;


public class Register extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmpasswordField;
    private JButton registerButton;

    public AppController controller;
    
    public Register(AppController controller){
        this.controller = controller;

        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel confirmpasswordLabel = new JLabel("Confirm Password:");
        confirmpasswordField = new JPasswordField();
        registerButton = new JButton("Register");
        JCheckBox isSellercheckbox = new JCheckBox("Register as Seller?",false);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmpasswordLabel);
        panel.add(confirmpasswordField);
        panel.add(new JLabel());
        panel.add(isSellercheckbox);
        panel.add(new JLabel());
        panel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmpassword = new String(confirmpasswordField.getPassword());
                if(controller.checkPassword(password,confirmpassword) && !controller.checkUsername(username)){
                    dispose();
                    controller.currentUser = controller.addUser(username, password,isSellercheckbox.isSelected());
                    new HomePage(controller);
                }else if(!controller.checkPassword(password, confirmpassword)){
                    displayErrorMessage("Password doesn't match");
                }else{
                    displayErrorMessage("User already taken!");
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
        JOptionPane.showMessageDialog(this, errorMessage, "Register Error", JOptionPane.ERROR_MESSAGE);
    }
}
