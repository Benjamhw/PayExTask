package com.example.tvshows.utils;

import com.example.tvshows.entities.Episode;
import com.example.tvshows.entities.TvShow;

import java.util.Comparator;

public class TvShowUtils{
    public static Episode getTopEpisode(TvShow show) throws NullPointerException{
        if(show.getEpisodes().isEmpty()){
            throw new NullPointerException("No episodes found for show.");
        }
        Episode top = show.getEpisodes().stream().max(Comparator.comparingDouble(Episode::getRating)).get();
        return top;
    }
}
