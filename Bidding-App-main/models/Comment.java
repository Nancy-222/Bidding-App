package models;

public class Comment{

    public String text;
    public int user_id;
    public int item_id;

    public Comment(String text, int user_id,int item_id){
        this.text = text;
        this.user_id = user_id;
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return text+","+user_id+","+item_id;
    }

}