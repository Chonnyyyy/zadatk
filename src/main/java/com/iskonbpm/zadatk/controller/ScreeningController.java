package com.iskonbpm.zadatk.controller;

import com.iskonbpm.zadatk.dao.Movie;
import com.iskonbpm.zadatk.dao.Screening;
import com.iskonbpm.zadatk.dao.ScreeningWithTitle;
import com.iskonbpm.zadatk.service.MovieService;
import com.iskonbpm.zadatk.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/screening")
public class ScreeningController {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MovieService movieService;

    @PatchMapping("/movies/{movie_id}/screenings")
    public ResponseEntity<?> create(@PathVariable("movie_id") long movie_id,@RequestBody List<Screening> screeningList) {
        for(int i = 0; i < screeningList.size(); i++){
            Movie tmp = movieService.getMovie(movie_id);
            if(tmp == null ){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
            Screening screening = screeningList.get(i);
            screening.setEndTime(screening.getStartTime().plusMinutes(tmp.getRunningTime()).plusMinutes(30));
            for(Movie movie : movieService.getAllMovies()){

                for(Screening _screening : movie.getScreenings()){

                    if((_screening.getHall().equals(screening.getHall()) && !(screening.getEndTime().isBefore(_screening.getStartTime())
                            || screening.getStartTime().isAfter(_screening.getEndTime())))
                    )
                    {

                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }

                }
            }
            screening.setMovieId(movie_id);
            screeningService.saveScreening(screening);

            //return new ResponseEntity<>(screening, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(screeningService.getScreeningsByMovieId(movie_id), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreening(@PathVariable long id) {
        Optional<Screening> screening = screeningService.getScreeningById(id);
        return screening.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<?> getAllScreenings(){

        List<Screening> screeningsWithTitles = screeningService.getAllScreenings();
        List<ScreeningWithTitle> screeningWithTitleList = new ArrayList<>();
        for (Screening screeningsWithTitle : screeningsWithTitles) {

            ScreeningWithTitle screeningWithTitle = new ScreeningWithTitle();
            screeningWithTitle.setId(screeningsWithTitle.getId());
            screeningWithTitle.setMovieId(screeningsWithTitle.getMovieId());
            screeningWithTitle.setHall(screeningsWithTitle.getHall());
            screeningWithTitle.setStartTime(screeningsWithTitle.getStartTime());
            screeningWithTitle.setEndTime(screeningsWithTitle.getEndTime());


            Movie movie = movieService.getMovie(screeningsWithTitle.getMovieId());
            screeningWithTitle.setMovieTitle(movie.getName());
            screeningWithTitleList.add(screeningWithTitle);
        }
        if (screeningWithTitleList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(screeningWithTitleList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScreening(@PathVariable long id){
        if (screeningService.getScreeningById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(screeningService.getAllScreenings().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        else {
            screeningService.deleteScreeningById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
