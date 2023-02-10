package com.example.tvshows.utils;

import com.example.tvshows.entities.Network;
import com.example.tvshows.entities.TvShow;

import java.util.Comparator;

public class NetworkUtils {
    public static double getAverageRating(Network network){
        double avg = network
                .getShows()
                .stream()
                .reduce(0D, (a, b) -> a + b.getRating(), Double::sum) / (double) network.getShows().size();

        return Math.round(avg*10)/10D;
    }

    public static TvShow getTopShow(Network network){
        return network
                .getShows()
                .stream()
                .max(Comparator.comparingDouble(TvShow::getRating)).get();
    }
}
