package com.example.mypackage;

import model.Artist;
import model.ArtistSong;
import model.DataSource;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DataSource ds = new DataSource();

        if (!ds.open()){
            System.out.println("Couldn't open datasource for you");
            return;
        }

        List<Artist> artists = ds.queryTheArtists(DataSource.ASC_ORDER);
        if (artists == null){
            System.out.println("We don't have artists");
            return;
        }else {
            if (artists.size() == 0){
                System.out.println("No artist found");
            }else {
                for (Artist artist:artists){
                    System.out.println("========ARTISTS DETAILS========");
                    System.out.println("ID = " +artist.getId() + " <--> " + "Name = " + artist.getName());
                }

            }
        }

        List<String> albums = ds.queryTheAlbumForArtist("Maroon 5", DataSource.DESC_ORDER);
        if (albums == null){
            System.out.println("No albums, query failed");
            return;
        }else {
            if (albums.size() == 0){
                System.out.println("No album found");
            }else {
                for (String albumName:albums){
                    System.out.println(albumName);
                }
            }
        }

        List<ArtistSong> artistSongs = ds.queryTheArtistForSong("24K Magic", DataSource.DESC_ORDER);
        if (artistSongs == null){
            System.out.println("No artist for specified song, query failed");
        }else {
            if (artistSongs.size() == 0){
                System.out.println("No artist for specified song");
            }else {
                for (ArtistSong artistSong:artistSongs){
                    System.out.println("Artist name = " + artistSong.getArtistName());
                    System.out.println("Album name = " + artistSong.getAlbumName());
                    System.out.println("Track num = " + artistSong.getTrack());
                }
            }
        }

        ds.queryTheSongMetaData();

        ds.close();
    }
}
