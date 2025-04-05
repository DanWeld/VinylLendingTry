package com.vinyl.shared;

import java.io.Serializable;

public class Vinyl implements Serializable {
    private String title;
    private String artist;
    private String owner;
    private String borrower;
    private boolean isAvailable;

    public Vinyl(String title, String artist, String owner, int i) {
        this.title = title;
        this.artist = artist;
        this.owner = owner;
        this.isAvailable = true;
        this.borrower = null;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getOwner() {
        return owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
        this.isAvailable = false;
    }

    public void returnVinyl() {
        this.borrower = null;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return title + " by " + artist + " (Owner: " + owner + 
               (borrower != null ? ", Borrowed by: " + borrower : "") + ")";
    }
} 