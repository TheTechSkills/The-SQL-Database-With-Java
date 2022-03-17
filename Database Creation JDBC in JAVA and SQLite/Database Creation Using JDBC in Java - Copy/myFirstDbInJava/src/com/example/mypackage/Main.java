package com.example.mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
	    try(Connection connection = DriverManager.getConnection("jdbc:sqlite:G:\\Developing\\SQL Database With Java\\Programs\\Database Creation JDBC in JAVA and SQLite\\Database Creation Using JDBC in Java\\myFirstDbInJava\\myfirstdbinjava.db");
            Statement statement = connection.createStatement()) {
            //Connection connection = DriverManager.getConnection("jdbc:sqlite:G:\\Developing\\SQL Database With Java\\Programs\\Database Creation JDBC in JAVA and SQLite\\Database Creation Using JDBC in Java\\myFirstDbInJava\\myfirstdbinjava.db");
            //Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE contacts (name TEXT, phone INTEGER, email TEXT)");

        }catch (SQLException e){
            System.out.println("Ops! Something went wrong "+e.getMessage());
        }
    }
}
