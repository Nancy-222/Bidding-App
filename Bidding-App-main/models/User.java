package models;

import java.io.*;
import java.util.ArrayList;

public class User{
    static int auto_id;
    static{
        try{
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            auto_id = Integer.parseInt(reader.readLine());
            reader.close();
            
        }catch(FileNotFoundException e){
            try{
                FileWriter writer = new FileWriter("users.txt");
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
    public String username;
    public String password;
    public boolean isSeller;

    public User(int id,String username, String password, boolean isSeller){
        this.id = id;
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
    }

    public User(String username, String password, boolean isSeller){
        this(auto_id,username,password,isSeller);
        auto_id++;
        try{
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();

            FileWriter writer = new FileWriter("users.txt");
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

    public User(String username, String password){
        this(username, password, false);
    }

    @Override
    public String toString() {
        return id+","+ username +","+ password+","+isSeller;
    }

}