package models;

import java.io.*;
import java.util.ArrayList;

public class Item{
    static int auto_id;
    static{
        try{
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            auto_id = Integer.parseInt(reader.readLine());
            reader.close();
            
        }catch(FileNotFoundException e){
            try{
                FileWriter writer = new FileWriter("items.txt");
                writer.write("0");
                writer.write("\n");
                writer.close();
                auto_id = 0;
            }catch(IOException e2){
                System.out.println("Error :" + e2.getMessage());
            }
        }catch(IOException e2){
            System.out.println("Error :" + e2.getMessage());
        }
    }
    public int id;
    public String title;
    public String description;
    public double price;
    public String category;
    public String image;
    public boolean closed;
    public int user_id;

    public Item(int id,String title, String description, double price,String category, String image, boolean closed, int user_id){
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.closed = closed;
        this.user_id = user_id;
    }

    public Item(String title, String description, double price,String category, String image, boolean closed, int user_id){
        this(auto_id,title,description,price,category,image,closed,user_id);
        auto_id++;
        try{
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();

            FileWriter writer = new FileWriter("items.txt");
            writer.write(String.valueOf(auto_id));
            writer.write("\n");
            for(int i =0; i < lines.size(); i++){
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.close();    
            
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Item(String title, String description, double price,String category, String image, int user_id){
        this(title,description,price,category,image,false,user_id);
    }

    @Override
    public String toString() {
        return id+","+ title +","+ description+","+price+","+category+","+image+","+closed+","+user_id;
    }

}