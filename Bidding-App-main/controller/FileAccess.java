package controller;

import java.io.*;
import java.util.ArrayList;

import models.*;

public class FileAccess{
    public void writeToFile(String filename, String entry){
        try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
        writer.write(entry);
        writer.write("\n");
        writer.close();
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public ArrayList<User> readUsers(){
        ArrayList<User> users = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String [] att = line.split(",");
                User u = new User(Integer.parseInt(att[0]),att[1],att[2],Boolean.parseBoolean(att[3]));
                users.add(u);
            }
            reader.close();
        }catch(Exception e){
        }
        return users;
    }

    public ArrayList<Item> readItems(){
        ArrayList<Item> items = new ArrayList<>();
        try{
            
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String [] att = line.split(",");
                Item i = new Item(Integer.parseInt(att[0]),att[1],att[2],Double.parseDouble(att[3]),att[4],att[5],Boolean.parseBoolean(att[6]),Integer.parseInt(att[7]));
                items.add(i);
            }
            reader.close();
        }catch(Exception e){
        }
        return items;
    }

    public ArrayList<Bid> readBids(){
        ArrayList<Bid> bids = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("bids.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String [] att = line.split(",");
                Bid b = new Bid(Double.parseDouble(att[0]),Integer.parseInt(att[1]),Integer.parseInt(att[2]));
                bids.add(b);
            }
            reader.close();
        }catch(Exception e){
        }
        return bids;
    }

    public ArrayList<Comment> readComments(){
        ArrayList<Comment> comments = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("comments.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String [] att = line.split(",");
                Comment c = new Comment(att[0],Integer.parseInt(att[1]),Integer.parseInt(att[2]));
                comments.add(c);
            }
            reader.close();
        }catch(Exception e){
        }
        return comments;
    }

    public ArrayList<Watchlist> readWatchlists(){
        ArrayList<Watchlist> watchlists = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("watchlists.txt"));
            String line;
            while((line = reader.readLine()) != null){
                String [] att = line.split(",");
                Watchlist w = new Watchlist(Integer.parseInt(att[0]),Integer.parseInt(att[1]));
                watchlists.add(w);
            }
            reader.close();
        }catch(Exception e){
        }
        return watchlists;
    }

    public void resetfile(String filename) {
        if(!filename.equals("users.txt") && !filename.equals("items.txt")) {
            resetfile_no_id(filename);
            return;
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line1 = reader.readLine();
            reader.close();
            FileWriter writer =  new FileWriter(filename, false);
            writer.write(line1);
            writer.write("\n");
            writer.close();
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    }

    private void resetfile_no_id(String filename){
        try{
            new FileWriter(filename, false).close();
        }catch(IOException e){
            System.out.println("Error: "+ e);
        }
    }
}
