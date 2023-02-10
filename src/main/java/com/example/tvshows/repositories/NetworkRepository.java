package com.example.tvshows.repositories;

import com.example.tvshows.entities.Network;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NetworkRepository extends JpaRepository<Network, Long> {
    boolean existsByExternalId(Long externalId);
    Network getByExternalId(Long externalId);

}
