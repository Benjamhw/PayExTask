package com.example.tvshows.repositories;

import com.example.tvshows.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    boolean existsByExternalId(Long externalId);
}
