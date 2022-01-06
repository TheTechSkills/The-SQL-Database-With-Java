package com.example.mypackage;

import model.Artist;
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

        List<Artist> artists = ds.queryTheArtists(DataSource.NO_ORDER);
        if (artists == null){
            System.out.println("We don't have artists");
            return;
        }
        for (Artist artist:artists){
            System.out.println("========ARTISTS DETAILS========");
            System.out.println("ID = " +artist.getId() + " <--> " + "Name = " + artist.getName());
        }
        ds.close();
    }
}
