package com.example.tvshows.repositories;

import com.example.tvshows.entities.Network;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<Network, Long> {
    boolean existsByExternalId(Long externalId);
    Network getByExternalId(Long externalId);
}
