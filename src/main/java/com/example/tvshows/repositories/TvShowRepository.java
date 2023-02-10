package com.example.tvshows.repositories;

import com.example.tvshows.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    boolean existsByExternalId(Long externalId);

    List<TvShow> findTop10ByOrderByRatingDesc();
}
