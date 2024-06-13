package com.iskonbpm.zadatk.dao;

import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByMovieId(long movieId);
}
