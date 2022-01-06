package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private Connection connection;
    private PreparedStatement queryViewSongTitleInfoView;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;
    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;
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

    public static final String QUERY_ARTIST_START_STRING ="SELECT * FROM " + TABLE_ARTISTS;
    public static final String  QUERY_ARTIST_SORT_STRING = " ORDER BY " + TABLE_ARTISTS + '.' +COLUMN_ARTISTS_NAME + " COLLATE NOCASE ";


    public static final String QUERY_THE_ALBUM_FOR_THE_ARTIST_START_STRING =
            "SELECT " + TABLE_ALBUMS +  '.' + COLUMN_ARTISTS_NAME + " FROM " +TABLE_ALBUMS + " INNER JOIN " +
                    TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_ARTIST+ " = " + TABLE_ARTISTS +
                    '.' + COLUMN_ARTISTS_ID + " WHERE " + TABLE_ARTISTS + '.' +COLUMN_ARTISTS_NAME + "=\"";
    public static final String QUERT_THE_ALBUM_FOR_THE_ARTIST_SORT_STRING = " ORDER BY " + TABLE_ALBUMS +
            '.' + COLUMN_ARTISTS_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_SONG_START_STRING = "SELECT "+ TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME + ", " +
            TABLE_ALBUMS + '.' + COLUMN_ARTISTS_NAME + ", " +
            TABLE_SONGS + '.' + COLUMN_SONG_TRACK + " FROM " +TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + '.' +COLUMN_SONG_ALBUM + " = "
            +TABLE_ALBUMS + '.' + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' +
            COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_ID + " WHERE " + TABLE_SONGS + '.' +
            COLUMN_SONG_TITLE +  "=\"";

    public static final String QUERY_ARTIST_SONG_SORT_STRING = " ORDER BY " + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME+
            ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";
    public static final String TABLE_ARTISTS_SONG_VIEW = "artist_list";
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTISTS_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME + ", " +
            TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " + TABLE_SONGS +
            '.' + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + '.' + COLUMN_SONG_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " +  TABLE_ALBUMS +  " ON " + TABLE_SONGS + '.' +
            COLUMN_SONG_ALBUM + " = " +TABLE_ALBUMS+  '.' + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " +TABLE_ALBUMS+ '.' + COLUMN_ALBUM_ARTIST +
            " = " +TABLE_ARTISTS + '.' +COLUMN_ARTISTS_ID +
            " ORDER BY "+
            TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME + ", "+
            TABLE_ALBUMS + '.' + COLUMN_ARTISTS_NAME + ", "+
            TABLE_SONGS + '.' + COLUMN_SONG_TRACK;
    public static final String QUERY_VIEW_SONG_TITLE_INFO = "SELECT " + COLUMN_ARTISTS_NAME + ", "+
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTISTS_SONG_VIEW + " WHERE " +
            COLUMN_SONG_TITLE + " =\"";
    public static final String QUERY_VIEW_SONG_TITLE_INFO_PREP_STMT = "SELECT " + COLUMN_ARTISTS_NAME + ", "+
            COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTISTS_SONG_VIEW + " WHERE " +
            COLUMN_SONG_TITLE + " = ?";

    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS + '(' +
            COLUMN_ARTISTS_NAME + ')' + "VALUES(?)";
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS + '(' +
            COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ')' + "VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS + '(' +
            COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM + ')' +
            "VALUES(?,?,?)";

    public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTISTS_ID + " FROM " +TABLE_ARTISTS +
            " WHERE " + COLUMN_ALBUM_NAME + " = ?";
    public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS +
            " WHERE " + COLUMN_ALBUM_NAME + " = ?";




    public boolean open(){
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            queryViewSongTitleInfoView = connection.prepareStatement(QUERY_VIEW_SONG_TITLE_INFO_PREP_STMT);
            insertIntoArtists = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = connection.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = connection.prepareStatement(INSERT_SONGS);
            queryArtist = connection.prepareStatement(QUERY_ARTIST);
            queryAlbum = connection.prepareStatement(QUERY_ALBUM);
            return true;
        }catch (SQLException e){
            System.out.println("Ups! Something went wrong " +e.getMessage());
            return false;
        }
    }

    public void close(){
        try {
            if (queryViewSongTitleInfoView != null){
                queryViewSongTitleInfoView.close();
            }

            if (insertIntoArtists != null){
                insertIntoArtists.close();
            }

            if (insertIntoAlbums != null){
                insertIntoAlbums.close();
            }

            if (insertIntoSongs != null){
                insertIntoSongs.close();
            }

            if (queryArtist != null){
                queryArtist.close();
            }

            if (queryAlbum != null){
                queryAlbum.close();
            }
            
            if (connection != null){
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Ups! Unable to close connection " + e.getMessage());
        }
    }

    public List<Artist> queryTheArtists(int orderOfSort){
        StringBuilder queryString = new StringBuilder(QUERY_ARTIST_START_STRING);

        appendOrderString(orderOfSort, queryString, QUERY_ARTIST_SORT_STRING);
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

    public List<String> queryTheAlbumForArtist(String artistName, int orderOfSort){
        StringBuilder queryString = new StringBuilder(QUERY_THE_ALBUM_FOR_THE_ARTIST_START_STRING);
        queryString.append(artistName);
        queryString.append("\"");

        appendOrderString(orderOfSort,queryString, QUERT_THE_ALBUM_FOR_THE_ARTIST_SORT_STRING);

        System.out.println(queryString);

        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryString.toString())){

            List<String> albums = new ArrayList<>();
            while (resultSet.next()){
                albums.add(resultSet.getString(1));
            }
            return albums;

        }catch (SQLException e ){
            System.out.println("Failed to query: " + e.getMessage());
            return null;
        }

    }

    public List<ArtistSong> queryTheArtistForSong(String songName, int orderOfSort){
        StringBuilder queryString = new StringBuilder(QUERY_ARTIST_SONG_START_STRING);
        queryString.append(songName);
        queryString.append("\"");

        appendOrderString(orderOfSort,queryString, QUERY_ARTIST_SONG_SORT_STRING);
        System.out.println(queryString);
        return loopSongArtistResults(queryString);
    }

    public void queryTheSongMetaData(){
        String queryString = "SELECT * FROM "+ TABLE_SONGS;
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryString)){
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int numOfColumns = resultSetMetaData.getColumnCount();

            System.out.println("The number of columns for table songs: "+numOfColumns);

            for (int i=1; i<=numOfColumns; i++){
                System.out.println("The column " + i + " in table songs has the name: " + resultSetMetaData.getColumnName(i));
            }
        }catch (SQLException e){
            System.out.println("Failed to query: "+e.getMessage());
        }
    }

    public int getCount(String tableName){
        String queryString = "SELECT COUNT(*) FROM " + tableName;
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(queryString)){
            return resultSet.getInt(1);
        }catch (SQLException e){
            System.out.println("Failed to get count " + e.getMessage());
            return -1;
        }
    }

    public boolean createTheViewForSongArtists(){
        try (Statement statement = connection.createStatement()){
            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;
        }catch (SQLException e){
            System.out.println("Failed to create the view " + e.getMessage());
            return false;
        }
    }

    public List<ArtistSong> queryTheSongInfoView(String songTitle){

        try {
            queryViewSongTitleInfoView.setString(1,songTitle);
            ResultSet resultSet = queryViewSongTitleInfoView.executeQuery();

            List<ArtistSong> artistSongs = new ArrayList<>();
            while (resultSet.next()){
                ArtistSong artistSong = new ArtistSong();
                artistSong.setArtistName(resultSet.getString(1));
                artistSong.setAlbumName(resultSet.getString(2));
                artistSong.setTrack(resultSet.getInt(3));
                artistSongs.add(artistSong);
            }
            return artistSongs;
        }catch (SQLException e){
            System.out.println("Failed to query "+ e.getMessage());
            return null;
        }
    }

    private List<ArtistSong> loopSongArtistResults(StringBuilder queryString){
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(queryString.toString())){

            List<ArtistSong> artistSongs = new ArrayList<>();
            while (resultSet.next()){
                ArtistSong artistSong = new ArtistSong();
                artistSong.setArtistName(resultSet.getString(1));
                artistSong.setAlbumName(resultSet.getString(2));
                artistSong.setTrack(resultSet.getInt(3));
                artistSongs.add(artistSong);
            }
            return artistSongs;
        }catch (SQLException e){
            System.out.println("Failed to query "+ e.getMessage());
            return null;
        }
    }
    private void appendOrderString(int orderOfSort, StringBuilder queryString, String sortInitialString){
        if (orderOfSort != NO_ORDER){
            queryString.append(sortInitialString);
            if (orderOfSort == ASC_ORDER){
                queryString.append("ASC");
            }else {
                queryString.append("DESC");
            }
        }
    }


}
