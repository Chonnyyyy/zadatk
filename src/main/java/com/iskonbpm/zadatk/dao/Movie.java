package com.iskonbpm.zadatk.dao;



import jakarta.persistence.*;

import java.util.List;


@Entity
public class Movie {

    @Id
    @GeneratedValue()
    private long id;

    private String name;
    private String directedBy;
    private int releaseYear;
    private int runningTime;

    @ElementCollection
    private List<String> genres;

    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screening> screenings;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDirectedBy(){
        return directedBy;
    }
    public void setDirectedBy(String directedBy){
        this.directedBy = directedBy;
    }

    public int getReleaseYear(){
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear){
        this.releaseYear = releaseYear;
    }

    public int getRunningTime(){
        return runningTime;
    }
    public void setRunningTime(int runningTime){
        this.runningTime = runningTime;
    }

    public List<String> getGenres(){
        return genres;
    }
    public void setGenres(List<String> genres){
        this.genres = genres;
    }

    public List<Screening> getScreenings(){
        return screenings;
    }
    public void setScreenings(List<Screening> screenings){
        this.screenings = screenings;
    }
}
