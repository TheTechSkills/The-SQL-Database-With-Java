package com.example.mypackage;

import model.DataSource;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DataSource ds = new DataSource();

        if (!ds.open()){
            System.out.println("Couldn't open datasource for you");
            return;
        }
        ds.close();
    }
}
