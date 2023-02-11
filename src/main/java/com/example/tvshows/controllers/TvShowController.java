package com.example.tvshows.controllers;

import com.example.tvshows.entities.Episode;
import com.example.tvshows.entities.TvShow;
import com.example.tvshows.repositories.TvShowRepository;
import com.example.tvshows.utils.TvShowUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class TvShowController {
    private TvShowRepository repository;

    TvShowController(TvShowRepository repository){
        this.repository = repository;
    }

    @GetMapping(value = "/tvshow/top10", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody byte[] getTop10(){

        List<TvShow> top10 = repository.findTop10ByOrderByRatingDesc();
        StringBuilder outputString = new StringBuilder();

        top10.forEach(show -> {
            StringBuilder line = new StringBuilder();
            line.append(show.getName() + ";");
            line.append(show.getNetwork().getName() + ";");
            line.append(show.getRating());
            line.append("\n");
            outputString.append(line);
        });

        return outputString.toString().getBytes(StandardCharsets.UTF_8);
    }

    @GetMapping(value = "/tvshow/summary", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody byte[] listTvShows(){

        List<TvShow> tvShows = repository.findAll();
        StringBuilder outputString = new StringBuilder();

        tvShows.forEach(show -> {
            StringBuilder line = new StringBuilder();
            line.append(show.getName()).append(";");
            line.append(show.getNetwork().getName()).append(";");
            show.getGenres().forEach((genre) -> {
                line.append(genre.getName()).append(",");
            });
            line.append(";");
            line.append(show.getEpisodes().size());
            line.append("\n");
            outputString.append(line);
        });

        return outputString.toString().getBytes(StandardCharsets.UTF_8);
    }

    @GetMapping(value = "/tvshow/top-episode", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody byte[] listTvShowsWithTopEpisode(){
        List<TvShow> tvShows = repository.findAll();
        StringBuilder outputString = new StringBuilder();

        tvShows.forEach(show -> {
            StringBuilder line = new StringBuilder();
            line.append(show.getName()).append(";");
            line.append(show.getNetwork().getName()).append(";");
            show.getGenres().forEach((genre) -> {
                line.append(genre.getName()).append(",");
            });
            line.append(";");

            try{
                Episode topEpisode = TvShowUtils.getTopEpisode(show);

                line.append(topEpisode.getSeason()).append(";");
                line.append(topEpisode.getEpisode()).append(";");
                line.append(topEpisode.getRating());
            }
            catch(NullPointerException e){
                line.append(";;");
            }

            line.append("\n");

            outputString.append(line);
        });

        return outputString.toString().getBytes(StandardCharsets.UTF_8);
    }


}
