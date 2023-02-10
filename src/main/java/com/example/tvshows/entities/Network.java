package com.example.tvshows.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Network {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "network", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TvShow> shows;
    private Long externalId;
    public Network() {
        super();
    }

    public Network(String name, List<TvShow> shows) {
        this.name = name;
        this.shows = shows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TvShow> getShows() {
        return shows;
    }

    public void setShows(List<TvShow> shows) {
        this.shows = shows;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return "Network{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shows=" + shows +
                '}';
    }
}
