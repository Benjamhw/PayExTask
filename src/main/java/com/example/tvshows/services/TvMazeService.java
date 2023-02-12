package com.example.tvshows.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.WebUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TvMazeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<?> entity;
    private final String baseUrl = "https://api.tvmaze.com";
    private final String singleSearchUrl = "/singlesearch/shows?q=";
    private final String episodeListUrl = "/shows/%d/episodes";

    public TvMazeService() {
        this.headers.set("User-Agent", "Candidate's Thesis-Application for a job interview at PayEx.");
        this.entity = new HttpEntity<>(headers);
    }

    public ResponseEntity<String> singleSearch(String showName) throws Exception{
        String encoded = URLEncoder.encode(showName, StandardCharsets.UTF_8);
        String url = baseUrl + singleSearchUrl + encoded;
        url = URLDecoder.decode(url, StandardCharsets.UTF_8);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> episodeListSearch(Long id){
        String url = String.format(baseUrl + episodeListUrl, id);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
