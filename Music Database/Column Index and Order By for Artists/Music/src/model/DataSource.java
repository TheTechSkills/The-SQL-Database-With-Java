package model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private Connection connection;
    public static final String DB_NAME = "sound.db";
    public static final String CONNECTION_STRING ="jdbc:sqlite:G:\\Developing\\SQL Database With Java\\Programs\\Music Database\\Music\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID ="_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";

    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID= "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";

    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS ="songs";
    public static final String COLUMN_SONG_ID="_id";
    public static final String COLUMN_SONG_TRACK ="track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM="album";

    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int NO_ORDER = 0;
    public static final int ASC_ORDER = 1;
    public static final int DESC_ORDER = 2;

    public boolean open(){
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }catch (SQLException e){
            System.out.println("Ups! Something went wrong " +e.getMessage());
            return false;
        }
    }

    public void close(){
        try {
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Ups! Unable to close connection " + e.getMessage());
        }
    }

    public List<Artist> queryTheArtists(int orderOfSort){
        StringBuilder queryString = new StringBuilder("SELECT * FROM ");
        queryString.append(TABLE_ARTISTS);
        if (orderOfSort != NO_ORDER){
            queryString.append(" ORDER BY ");
            queryString.append(COLUMN_ARTISTS_NAME);
            queryString.append(" COLLATE NOCASE ");
            if (orderOfSort == ASC_ORDER){
                queryString.append("ASC");
            }else {
                queryString.append("DESC");
            }
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryString.toString())){

            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()){
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(INDEX_ARTIST_ID));
                artist.setName(resultSet.getString(INDEX_ALBUM_NAME));
                artists.add(artist);
            }
            return artists;

        }catch (SQLException e){
            System.out.println("Failed to Query " +e.getMessage());
            return null;
        }
    }

}
