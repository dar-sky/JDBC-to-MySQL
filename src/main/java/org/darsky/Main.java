package org.darsky;


import java.sql.*;
import java.util.Random;

public class Main {

    public static String RName()
    {
        Random rand = new Random();

        // Generate random string of up to 10 characters
        String randStr = "";
        int strLength = rand.nextInt(11); // Random number between 0 and 10
        for (int i = 0; i < strLength; i++) {
            char c = (char)(rand.nextInt(26) + 'a'); // Random lowercase letter
            randStr += c;
        }

        return randStr;
    }
    public static int RAge()
    {
        Random rand = new Random();

        return rand.nextInt(101);
    }
    public static void printData(ResultSet res)
    {
        try
        {
            System.out.println("Printing all items from table >> ");
            while (res.next())
            {
                System.out.print("\t\t[ ");
                System.out.print("id: " +res.getInt("id") + ", ");
                System.out.print("name: " + res.getString("name") + ", ");
                System.out.print("age: " + res.getInt("age"));
                System.out.print(" ]\n");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Problem in printing ....");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello JDBC!\n");

        MySQL mysql = new MySQL("MyDatabase", "MyTable");

        mysql.init();

        try {

            System.out.println("Adding 5 items to table");
            for(int i=0;i<10;i++){
                int rowsAffected = mysql.addItem(RName(),RAge());
//                System.out.println("\t\t" + rowsAffected + " status ok");
            }

            ResultSet res = mysql.getItems();
            printData(res);

            System.out.println("Cleaning database");
            mysql.destroy();
        }
        catch (SQLException e)
        {
            System.out.println("Process Failed ");
            System.out.println(e.getMessage());
        }
    }
}