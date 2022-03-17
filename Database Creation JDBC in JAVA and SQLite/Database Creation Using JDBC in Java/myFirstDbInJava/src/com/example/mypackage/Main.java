package com.example.mypackage;

import java.sql.*;

public class Main {
    public static final String DB_NAME = "myfirstdbinjava.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:G:\\Developing\\SQL Database With Java\\Programs\\Database Creation JDBC in JAVA and SQLite\\Database Creation Using JDBC in Java\\myFirstDbInJava\\" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PHONE="phone";
    public static final String COLUMN_EMAIL="email";

    public static void main(String[] args) {
	    try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF exists " +TABLE_CONTACTS);
            statement.execute("CREATE TABLE IF NOT EXISTS " +TABLE_CONTACTS +
                    " (" + COLUMN_NAME + " text, " + COLUMN_PHONE +  " INTEGER, " +
                    COLUMN_EMAIL + " text" + ")");

            insertContacts(statement,"Faraday", 4687984, "faraday@somewhere.com" );
            insertContacts(statement,"Einstein", 55630, "einstein@somewhere.com");
            insertContacts(statement,"Newton", 684794, "newton@somewhere.com");

            statement.execute("UPDATE " +TABLE_CONTACTS +
                            " SET " + COLUMN_EMAIL + "='einsteinTwo@here.com'" +  "WHERE "
                    +COLUMN_NAME + "='Einstein'");
            statement.execute("DELETE FROM "  +
                            TABLE_CONTACTS + " WHERE " + COLUMN_NAME + " ='Newton'");


            ResultSet resultSet = statement.executeQuery("SELECT * FROM " +TABLE_CONTACTS);
            while (resultSet.next()){
                System.out.println(resultSet.getString(COLUMN_NAME) + " -- " +
                                    resultSet.getString(COLUMN_PHONE ) + " -- " +
                                    resultSet.getString(COLUMN_EMAIL));
            }
            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e){
            System.out.println("Ops! Something went wrong "+e.getMessage());
        }
    }

    private static void insertContacts(Statement statement, String name, int phone, String email) throws SQLException {
        statement.execute("INSERT INTO " + TABLE_CONTACTS + "(" + COLUMN_NAME + ", "
                +COLUMN_PHONE + ", "+
                COLUMN_EMAIL + ") " + "VALUES (" + "'"+name+"', " +phone+ ", '" + email+"'" + ")");
    }
}

