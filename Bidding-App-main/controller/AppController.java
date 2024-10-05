package controller;

import java.util.ArrayList;

import models.*;



public class AppController {
    private FileAccess fa;
    public User currentUser;

    public AppController(){
        fa = new FileAccess();
    }

    public User addUser(String username, String password, boolean isSeller){
        User u = new User(username, password,isSeller);
        fa.writeToFile("users.txt", u.toString());
        return u;
    }

    public void addItem(String title, String description, double price,String category, String image, boolean closed, int user_id){
        Item i = new Item(title,description,price,category,image,closed,user_id);
        fa.writeToFile("items.txt", i.toString());
    }

    public Bid addBid(double bid, int user_id, int item_id){
        Bid b = new Bid(bid, user_id, item_id);
        fa.writeToFile("bids.txt", b.toString());
        return b;
    }

    public Comment addComment(String text, int user_id, int item_id){
        Comment c = new Comment(text, user_id, item_id);
        fa.writeToFile("comments.txt", c.toString());
        return c;
    }

    public void addWatchlist(int user_id, int item_id){
        Watchlist w = new Watchlist(user_id, item_id);
        fa.writeToFile("watchlists.txt", w.toString());
    }

    public void removeWatchlist(int user_id, int item_id){
        ArrayList<Watchlist> watchlists = fa.readWatchlists();
        
        watchlists.removeIf(watchlist -> watchlist.user_id == user_id && watchlist.item_id==item_id);
        fa.resetfile("watchlists.txt");
        for (Watchlist watchlist : watchlists){
            fa.writeToFile("watchlists.txt", watchlist.toString());
        }
    }

    public void closeItem(int item_id){
        ArrayList<Item> items = fa.readItems();

        fa.resetfile("items.txt");
        for (Item item : items) {
            if(item.id == item_id){
                item.closed = true;
            }
            fa.writeToFile("items.txt", item.toString());
        }
    }

    public ArrayList<Item> getAllItems(){
        return fa.readItems();
    }

    public User checkUser(String username, String password){
        ArrayList<User> users = fa.readUsers();
        for (User user : users) {
            if(user.username.equals(username) && user.password.equals(password)){
                return user;
            }
        }
        return null;
    }

    public boolean checkUsername(String username){
        ArrayList<User> users = fa.readUsers();
        for (User user : users) {
            if(user.username.equals(username)){
                return true;
            }
        }
        return false;
    }

    public User getUser(int id){
        ArrayList<User> users = fa.readUsers();

        for (User user : users) {
            if(user.id == id){
                return user;
            }
        }
        return null;
    }

    public Item getItem(int id){
        ArrayList<Item> items = fa.readItems();

        for (Item item : items) {
            if(item.id == id){
                return item;
            }
        }
        return null;
    }

    public ArrayList<Comment> getComments(int item_id){
        ArrayList<Comment> allcomments = fa.readComments();
        ArrayList<Comment> itemcomments = new ArrayList<>();

        for (Comment comment : allcomments) {
            if(comment.item_id == item_id){
                itemcomments.add(comment);
            }
        }
        return itemcomments;
    }

    public ArrayList<Bid> getBids(int item_id){
        ArrayList<Bid> allbids = fa.readBids();
        ArrayList<Bid> itembids = new ArrayList<>();

        for (Bid bid : allbids) {
            if(bid.item_id == item_id){
                itembids.add(bid);
            }
        }
        return itembids;
    }

    public ArrayList<Item> getWatchlist(int user_id){
        ArrayList<Watchlist> allwatchlists = fa.readWatchlists();
        ArrayList<Item> userwatchlist = new ArrayList<>();

        for (Watchlist watchlist : allwatchlists) {
            if(watchlist.user_id == user_id){
                userwatchlist.add(getItem(watchlist.item_id));
            }
        }
        return userwatchlist;
    }

    public User getMaxBid(int item_id){
        ArrayList<Bid> allitembids = getBids(item_id);
        double max = 0;
        User maxUser = null;

        for (Bid bid : allitembids) {
            if(bid.bid > max){
                max = bid.bid;
                maxUser = getUser(bid.user_id);
            }
        }
        return maxUser;
    }

    public double getmaxbidvalue(int item_id){
        ArrayList<Bid> allitembids = getBids(item_id);
        double max = 0;

        for (Bid bid : allitembids) {
            if(bid.bid > max){
                max = bid.bid;
            }
        }
        return max;
    }
    public boolean checkBid(double value,int item_id){
        ArrayList<Bid> allitembids = getBids(item_id);
        Item item = getItem(item_id);

        if(allitembids.size()==0){
            return value >= item.price;
        }
        return value >= getmaxbidvalue(item_id);
    }

    public boolean checkPassword(String pass, String conf){
        return pass.equals(conf);
    }

    public boolean isItemWatchlisted(int user_id,int item_id){
        ArrayList<Item> items = getWatchlist(user_id);
        for (Item item : items) {
            if(item.id == item_id){
                return true;
            }
        }
        return false;
    }

}
