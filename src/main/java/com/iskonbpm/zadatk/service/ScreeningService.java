package com.iskonbpm.zadatk.service;

import com.iskonbpm.zadatk.dao.Screening;
import com.iskonbpm.zadatk.dao.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    @Autowired
    private ScreeningRepository screeningRepository;

    public Screening saveScreening(Screening screening){
        return screeningRepository.save(screening);
    }

    public List<Screening> getAllScreenings(){
        return screeningRepository.findAll();
    }

    public Optional<Screening> getScreeningById(long id){
        return screeningRepository.findById(id);
    }

    public List<Screening> getScreeningsByMovieId(Long movieId) {
        return screeningRepository.findByMovieId(movieId);
    }

    public void deleteScreeningById(long id){
        screeningRepository.deleteById(id);
    }
}
