package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.AppController;


public class CreateItem extends JFrame{
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JButton createButton;

    public AppController controller;
    
    public CreateItem(AppController controller){
        this.controller = controller;

        setTitle("Create Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        createButton = new JButton("Create");

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(new JLabel());
        panel.add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String desciption = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());

                controller.addItem(title, desciption, price,"", "", false, controller.currentUser.id);
                new HomePage(controller);
            }
        });

        add(panel);
        setVisible(true);
    }

    void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
