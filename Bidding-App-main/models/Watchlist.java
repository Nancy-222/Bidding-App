package models;

public class Watchlist{

    public int user_id;
    public int item_id;

    public Watchlist(int user_id,int item_id){
        this.user_id = user_id;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return user_id+","+item_id;
    }

}