package com.example.tvshows.controllers;

import com.example.tvshows.entities.TvShow;
import com.example.tvshows.repositories.TvShowRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TvShowController {
    private TvShowRepository repository;

    TvShowController(TvShowRepository repository){
        this.repository = repository;
    }

    @GetMapping("/tvshow/all")
    Iterable<TvShow> getAll(){
        return repository.findAll();
    }

    @GetMapping("/tvshow/{externalId}/exists")
    boolean exists(@PathVariable Long externalId){
        return repository.existsByExternalId(externalId);
    }
}
