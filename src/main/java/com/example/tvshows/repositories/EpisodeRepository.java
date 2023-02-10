package com.example.tvshows.repositories;

import com.example.tvshows.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    boolean existsByExternalId(Long externalId);
}
