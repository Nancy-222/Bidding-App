package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.AppController;
import models.Bid;
import models.Comment;
import models.Item;

public class ItemPage extends JFrame {
    private JLabel titleLabel;
    private JButton addToWatchlistButton;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JTextField bidField;
    private JButton placeBidButton;
    private JTextField commentField;
    private JButton commentButton;
    private boolean isWatchlisted;
    private DefaultListModel<Bid> listModel;
    private DefaultListModel<Comment> listModel2;
    private JButton closebidButton;
    private JLabel closebidLabel;
    private JLabel winbidLabel;

    AppController controller;

    public ItemPage(AppController controller,int itemId) {
        this.controller = controller;
        Item item = controller.getItem(itemId);
        setTitle("Item Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        titleLabel = new JLabel(item.title+" (Owner: "+controller.getUser(item.user_id).username+")");
        addToWatchlistButton = new JButton("Add to Watchlist");
        descriptionLabel = new JLabel(item.description);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(Color.WHITE);
        priceLabel = new JLabel("Price: $"+item.price);
        listModel = new DefaultListModel<>();
        setBidList(controller.getBids(itemId));
        JList<Bid> bidsList = new JList<>(listModel);
        bidsList.setCellRenderer(new BidCellRenderer(controller));
        listModel2 = new DefaultListModel<>();
        setCommentList(controller.getComments(itemId));
        JList<Comment> commentsList = new JList<>(listModel2);
        commentsList.setCellRenderer(new CommentCellRenderer(controller));
        bidField = new JTextField(10);
        placeBidButton = new JButton("Place Bid");
        commentField = new JTextField();
        commentButton = new JButton("Comment");
        closebidButton = new JButton("Close Bid");
        closebidLabel = new JLabel("This item is closed");
        closebidLabel.setForeground(Color.RED);
        winbidLabel = new JLabel("You Won the bid");
        winbidLabel.setForeground(Color.GREEN);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        if(controller.currentUser != null && !controller.currentUser.isSeller){
            isWatchlisted = controller.isItemWatchlisted(controller.currentUser.id,itemId);
            if(isWatchlisted){
                addToWatchlistButton.setText("Remove from Watchlist");
            }
            topPanel.add(addToWatchlistButton);
        }else if(controller.currentUser != null && !controller.getItem(itemId).closed){
            topPanel.add(closebidButton);
        }

        if(controller.getItem(itemId).closed){
            topPanel.add(closebidLabel);
            if(controller.currentUser !=null && controller.getMaxBid(itemId) != null && controller.getMaxBid(itemId).id == controller.currentUser.id){
                topPanel.add(winbidLabel);
            }
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel itemDetailsPanel = new JPanel(new GridLayout(4, 1));
        itemDetailsPanel.add(titleLabel);
        itemDetailsPanel.add(descriptionLabel);
        itemDetailsPanel.add(priceLabel);
        itemDetailsPanel.add(new JLabel("Bids by others:"));
        centerPanel.add(itemDetailsPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(bidsList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if(controller.currentUser != null && !controller.currentUser.isSeller && !controller.getItem(itemId).closed){
            bottomPanel.add(bidField);
            bottomPanel.add(placeBidButton);
        }

        mainPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(bottomPanel,BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createTitledBorder("Comments"));
        if(controller.currentUser != null){
            commentPanel.add(commentField, BorderLayout.CENTER);
            commentPanel.add(commentButton,BorderLayout.EAST);
        }
        commentPanel.add(new JScrollPane(commentsList),BorderLayout.SOUTH);
        mainPanel.add(commentPanel, BorderLayout.SOUTH);

        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isWatchlisted) {
                    isWatchlisted = false;
                    addToWatchlistButton.setText("Add to Watchlist");
                    controller.removeWatchlist(controller.currentUser.id, itemId);
                } else {
                    isWatchlisted = true;
                    addToWatchlistButton.setText("Remove from Watchlist");
                    controller.addWatchlist(controller.currentUser.id, itemId);
                }
            }
        });

        placeBidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bid = Double.parseDouble(bidField.getText());
                if(controller.checkBid(bid, itemId)){
                    listModel.addElement(controller.addBid(bid, controller.currentUser.id, itemId));
                }else{
                    displayErrorMessage("The bid should be higher than the max bid");
                }
                clearFields();
            }
        });

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text =commentField.getText();
                listModel2.add(0,controller.addComment(text, controller.currentUser.id, itemId));
                clearFields();
            }
        });

        closebidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.closeItem(itemId);
                topPanel.remove(closebidButton);
                topPanel.add(closebidLabel);
                topPanel.revalidate();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    public void setBidList(ArrayList<Bid> bids) {
        for (Bid bid : bids) {
            listModel.addElement(bid);
        }
    }
    public void setCommentList(ArrayList<Comment> comments) {
        for (Comment comment : comments) {
            listModel2.add(0,comment);
        }
    }
    void clearFields() {
        bidField.setText("");
        commentField.setText("");
    }
    void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Bid Error", JOptionPane.ERROR_MESSAGE);
    }
}

class BidCellRenderer extends JLabel implements ListCellRenderer<Bid> {
    private AppController controller;
    public BidCellRenderer(AppController controller) {
        this.controller = controller;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Bid> list, Bid value, int index,boolean isSelected, boolean cellHasFocus) {
        setText(controller. getUser(value.user_id).username + ": $" + value.bid);
        return this;
    }
}

class CommentCellRenderer extends JLabel implements ListCellRenderer<Comment> {
    private AppController controller;
    public CommentCellRenderer(AppController controller) {
        this.controller = controller;
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends Comment> list, Comment value, int index,boolean isSelected, boolean cellHasFocus) {
        setText(controller.getUser(value.user_id).username + ": " + value.text);
        return this;
    }
}
