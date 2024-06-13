package com.iskonbpm.zadatk.dao;

public class ScreeningWithTitle extends Screening{
    private String movieTitle;

    public ScreeningWithTitle() {
        super();
        movieTitle = null;
    }




    public String getMovieTitle(){
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle){
        this.movieTitle = movieTitle;
    }
}
