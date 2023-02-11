package com.example.tvshows.repositories;

import com.example.tvshows.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);
    Genre findFirstByName(String name);
}
