package com.example.tvshows.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TvMazeService {

    private RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://api.tvmaze.com";
    private final String singleSearchUrl = "/singlesearch/shows?q=";
    private final String episodeListUrl = "/shows/%d/episodes";

    public ResponseEntity<String> singleSearch(String showName) throws Exception{
        String encoded = URLEncoder.encode(showName, StandardCharsets.UTF_8);
        String url = baseUrl + singleSearchUrl + encoded;
        url = URLDecoder.decode(url,"UTF-8");

        /*

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Candidate's Thesis-Application for a job interview at PayEx.");
        HttpEntity<String> entity = new HttpEntity<String>("");

         */

        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<String> episodeListSearch(Long id){
        String url = String.format(baseUrl + episodeListUrl, id);
        return restTemplate.getForEntity(url, String.class);
    }
}
