package com.iskonbpm.zadatk.controller;


import com.iskonbpm.zadatk.dao.Movie;
import com.iskonbpm.zadatk.dao.Screening;
import com.iskonbpm.zadatk.service.MovieService;
import com.iskonbpm.zadatk.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ScreeningService screeningService;

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie){
        Movie savedMovie = movieService.saveMovie(movie);
        for (Screening screening : savedMovie.getScreenings()){
            screening.setMovieId(savedMovie.getId());
            screening.setEndTime(screening.getStartTime().plusMinutes(savedMovie.getRunningTime()));
            screeningService.saveScreening(screening);
        }

        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable long id){
        Movie movie = movieService.getMovie(id);
        if(Objects.isNull(movie)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        }
       // return movie.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies() {

        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }
}
