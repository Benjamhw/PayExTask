package com.example.tvshows.services;

import com.example.tvshows.entities.Episode;
import com.example.tvshows.entities.Network;
import com.example.tvshows.entities.TvShow;
import com.example.tvshows.factory.Factory;
import com.example.tvshows.repositories.EpisodeRepository;
import com.example.tvshows.repositories.NetworkRepository;
import com.example.tvshows.repositories.TvShowRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PopulateTvShowsService implements CommandLineRunner
{
    private EpisodeRepository episodeRepository;
    private TvShowRepository tvShowRepository;
    private NetworkRepository networkRepository;
    private TvMazeService tvMazeService = new TvMazeService();

    @Autowired
    public PopulateTvShowsService(
            EpisodeRepository episodeRepository,
            TvShowRepository tvShowRepository,
            NetworkRepository networkRepository) {
        this.episodeRepository = episodeRepository;
        this.tvShowRepository = tvShowRepository;
        this.networkRepository = networkRepository;
    }

    private ArrayList<String> readAndFormatShowListFile(){

        ArrayList<String> dataShowTitles = new ArrayList<>();

        try {
            File myObj = new File("/Users/benwee/Documents/Projects/PayEx/Task/tv-shows/src/main/resources/shows.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                dataShowTitles.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return dataShowTitles;
    }

    private void collectAndSaveTvShows(ArrayList<String> dataShowTitles) throws Exception{

        ObjectMapper mapper = new ObjectMapper();

        for(String showName : dataShowTitles) {
            ResponseEntity<String> response;
            JsonNode jsonRoot = null;

            for(int i = 0; i < 5; i++){
                try {
                    response = tvMazeService.singleSearch(showName);
                    if (response.getStatusCode().value() == 200){
                        jsonRoot = mapper.readTree(response.getBody());
                        break;
                    }
                }
                catch (HttpClientErrorException e){
                    if (e.getStatusCode().value() == 429){
                        System.out.println("Too many request, waiting 5 seconds..");
                        Thread.sleep(5000);
                    } else if (e.getStatusCode().value() == 404) {
                        System.out.println("Could not find show: " + showName);
                    }
                    else {
                        e.printStackTrace();
                        break;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
            if (jsonRoot == null) {
                System.out.println("Could not get a result for " + showName);
            } else {
                TvShow show = Factory.tvShowFactory(jsonRoot);
                Network network;
                Long networkId = jsonRoot.path("network").path("id").asLong();

                if(this.networkRepository.existsByExternalId(networkId)){
                    network = this.networkRepository.getByExternalId(networkId);
                } else {
                    network = Factory.networkFactory(jsonRoot.path("network"));
                }
                show.setNetwork(network);
                this.networkRepository.save(network);
                this.tvShowRepository.save(show);
            }
        }
    }

    private ArrayList<Episode> fetchEpisodesForTvShow(TvShow show) throws Exception{
        ArrayList<Episode> episodes = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response;
        JsonNode jsonRoot = null;

        for (int i = 0; i < 5; i++){
            try {
                response = tvMazeService.episodeListSearch(show.getExternalId());
                jsonRoot = mapper.readTree(response.getBody());
                break;

            } catch (HttpClientErrorException e) {
                if(e.getStatusCode().value() == 429){
                    System.out.println("Too many request, waiting 5 seconds..");
                    Thread.sleep(5000);
                }
                else if(e.getStatusCode().value() == 404){
                    System.out.println("Could not find episodes for " + show.getName());
                }
                else {
                    throw e;
                }
            } catch (Exception e){
                throw e;
            }

        }
        if (jsonRoot == null) {
            System.out.println("Could not get a episodes for " + show.getName());
        } else {
            jsonRoot.elements().forEachRemaining(jsonEpisode -> {
                Episode episode = Factory.episodeFactory(jsonEpisode);
                episode.setShow(show);
                episodes.add(episode);
            });
        }

        return episodes;
    }


    @Override
    public void run(String... strings) throws Exception {

        ArrayList<String> dataShowTitles = this.readAndFormatShowListFile();
        System.out.print("Fetching shows...");
        this.collectAndSaveTvShows(dataShowTitles);
        System.out.print(" DONE\n");

        Iterable<TvShow> shows = tvShowRepository.findAll();
        System.out.print("Fetching episodes...");
        shows.forEach(show -> {
            try {
                List<Episode> episodes = this.fetchEpisodesForTvShow(show);
                show.setEpisodes(episodes);
                tvShowRepository.save(show);
                episodeRepository.saveAll(episodes);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });
        System.out.print(" DONE\n");
        System.out.println("Data load complete");

    }
    // First, load the tvshow-list txt file
    // For now, using

    // For each show, call the external API

    // Save each show with all episodes to H2 database
}
