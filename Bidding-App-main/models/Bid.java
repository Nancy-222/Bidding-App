package models;

public class Bid{

    public double bid;
    public int user_id;
    public int item_id;

    public Bid(double bid, int user_id,int item_id){
        this.bid = bid;
        this.user_id = user_id;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return bid+","+user_id+","+item_id;
    }

}