package com.example.tvshows.factory;

import com.example.tvshows.entities.Episode;
import com.example.tvshows.entities.Network;
import com.example.tvshows.entities.TvShow;
import com.fasterxml.jackson.databind.JsonNode;

public class Factory {
    public static TvShow tvShowFactory(JsonNode json){

        TvShow tvShow = new TvShow();
        tvShow.setExternalId(json.path("id").asLong());
        tvShow.setName(json.path("name").asText());
        tvShow.setRating(json.path("rating").path("average").asDouble());
        tvShow.setImdbLink(json.path("externals").path("imdb").asText());
        tvShow.setImageUrl(json.path("image").path("medium").asText());
        //tvShow.setDiscription(json.path("summary").asText());

        return tvShow;
    }

    public static Network networkFactory(JsonNode json){
        Network network = new Network();
        network.setName(json.path("name").asText());
        network.setExternalId(json.path("id").asLong());

        return network;
    }

    public static Episode episodeFactory(JsonNode json){
        Episode episode = new Episode();
        episode.setExternalId(json.path("id").asLong());
        episode.setName(json.path("name").asText());
        episode.setSeason(json.path("season").asText());
        episode.setEpisode(json.path("number").asText());

        return episode;
    }
}
