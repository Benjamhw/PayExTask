package com.example.tvshows.controllers;

import com.example.tvshows.entities.Network;
import com.example.tvshows.entities.TvShow;
import com.example.tvshows.repositories.NetworkRepository;
import com.example.tvshows.utils.NetworkUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

@RestController
public class NetworkController {
    private NetworkRepository repository;

    NetworkController(NetworkRepository repository){
        this.repository = repository;
    }

    @GetMapping("/network/top")
    public @ResponseBody byte[] getNetworks(){

        List<Network> networks = repository.findAll();
        StringBuilder outputString = new StringBuilder();

        networks.sort((a,b) -> {
            double a_avg = NetworkUtils.getAverageRating(a);
            double b_avg = NetworkUtils.getAverageRating(b);
            return -Double.compare(a_avg, b_avg);
        });

        networks.forEach(network -> {

            double avg_rating = NetworkUtils.getAverageRating(network);

            TvShow topShow = NetworkUtils.getTopShow(network);
            int showCount = network.getShows().size();

            StringBuilder line = new StringBuilder();
            line.append(avg_rating + ";");
            line.append(network.getName() + ";");
            line.append(topShow.getName() + ";");
            line.append(topShow.getRating() + ";");
            line.append(showCount);
            line.append("\n");

            outputString.append(line);

        });

        return outputString.toString().getBytes(StandardCharsets.UTF_8);

    }
}
