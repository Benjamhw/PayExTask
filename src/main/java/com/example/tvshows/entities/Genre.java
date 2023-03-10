package com.example.tvshows.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany
    private List<TvShow> shows;

    public Genre(){
        super();
    }

    public Genre(String name) {
        super();
        this.name = name;
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
}
