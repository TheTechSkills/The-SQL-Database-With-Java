package com.example.mypackage;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
	    try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:G:\\Developing\\SQL Database With Java\\Programs\\Database Creation JDBC in JAVA and SQLite\\Database Creation Using JDBC in Java\\myFirstDbInJava\\myfirstdbinjava.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
            /*
            statement.execute("INSERT INTO contacts(name, phone, email) " +
                    "VALUES('Faraday', 4687984, 'faraday@somewhere.com')");
            statement.execute("INSERT INTO contacts(name, phone, email) " +
                    "VALUES('Einstein', 55630, 'einstein@somewhere.com')");
            statement.execute("INSERT INTO contacts(name, phone, email) " +
                    "VALUES('Newton', 684794, 'newton@somewhere.com')");*/
            //statement.execute("UPDATE contacts SET email ='einsteinTwo@here.com' WHERE name = 'Einstein'");
            //statement.execute("DELETE FROM contacts WHERE name = 'Newton'");


            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
            while (resultSet.next()){
                System.out.println(resultSet.getString("name") + " -- " +
                                    resultSet.getString("phone" ) + " -- " +
                                    resultSet.getString("email"));
            }
            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            System.out.println("Ops! Something went wrong "+e.getMessage());
        }
    }
}
