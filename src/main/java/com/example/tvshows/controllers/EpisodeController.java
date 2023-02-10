package com.example.tvshows.controllers;

import com.example.tvshows.entities.Episode;
import com.example.tvshows.repositories.EpisodeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EpisodeController {
    private EpisodeRepository repository;

    EpisodeController(EpisodeRepository episodeRepository){
        this.repository = episodeRepository;
    }

    @GetMapping("/testEpisodes")
    Iterable<Episode> getAll(){
        return repository.findAll();
    }

}
