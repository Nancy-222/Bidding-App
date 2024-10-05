package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.AppController;
import models.Item;

public class HomePage extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JButton logoutButton;
    private JButton addItemButton;
    private JLabel usernameLabel;
    private DefaultListModel<Item> listModel;
    private JButton mywatchlistButton;
    
    AppController controller;

    public HomePage(AppController controller) {
        this.controller = controller;
        setTitle("Auction Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(topRightPanel,BorderLayout.EAST);
        topPanel.add(topLeftPanel, BorderLayout.WEST);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        logoutButton = new JButton("Logout");
        addItemButton = new JButton("Add Item");
        usernameLabel = new JLabel(); 
        mywatchlistButton = new JButton("My Watchlist");

        if(controller.currentUser == null){
            topRightPanel.add(loginButton);
            topRightPanel.add(registerButton);
        }else{
            if(controller.currentUser.isSeller){
                topLeftPanel.add(addItemButton);
            }else{
                topLeftPanel.add(mywatchlistButton);
            }
            usernameLabel.setText(controller.currentUser.username);
            topRightPanel.add(usernameLabel);
            topRightPanel.add(logoutButton);
        }

        listModel = new DefaultListModel<>();
        setItemList(controller.getAllItems());
        JList<Item> itemList = new JList<>(listModel);
        itemList.setCellRenderer(new ItemCellRenderer());
        JScrollPane scrollPane = new JScrollPane(itemList);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(controller);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register(controller);
            }
        });

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item selected = itemList.getSelectedValue();
                if (selected != null) {
                    int itemId = selected.id;
                    new ItemPage(controller,itemId);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.currentUser = null;
                dispose();
                new HomePage(controller);
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateItem(controller);
            }
        });

        mywatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WatchlistPage(controller);
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    public void setItemList(ArrayList<Item> items) {
        for (Item item : items) {
            listModel.addElement(item);
        }
    }

}

class ItemCellRenderer extends JLabel implements ListCellRenderer<Item> {
    public ItemCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,boolean isSelected, boolean cellHasFocus) {
        setText(value.title + " - $" + value.price);
        setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
        return this;
    }
}
