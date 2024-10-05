package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.AppController;
import models.Item;

public class WatchlistPage extends JFrame {
    private DefaultListModel<Item> listModel;
    
    AppController controller;

    public WatchlistPage(AppController controller) {
        this.controller = controller;
        setTitle("Watchlist Homepage");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        setItemList(controller.getWatchlist(controller.currentUser.id));
        JList<Item> itemList = new JList<>(listModel);
        itemList.setCellRenderer(new WatchlistItemCellRenderer());
        JScrollPane scrollPane = new JScrollPane(itemList);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item selected = itemList.getSelectedValue();
                if (selected != null) {
                    int itemId = selected.id;
                    new ItemPage(controller,itemId);
                }
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

class WatchlistItemCellRenderer extends JLabel implements ListCellRenderer<Item> {
    public WatchlistItemCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index,boolean isSelected, boolean cellHasFocus) {
        setText(value.title + " - $" + value.price);
        setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
        return this;
    }
}
