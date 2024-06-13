package com.iskonbpm.zadatk.service;

import com.iskonbpm.zadatk.dao.Movie;
import com.iskonbpm.zadatk.dao.MovieRepository;
import com.iskonbpm.zadatk.dao.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){

        return movieRepository.findAll();
    }

    public Movie getMovie(long id){
        return movieRepository.findById(id).orElse(null);
    }

}
