package com.EventHorizon.EventHorizon.repository;

import com.EventHorizon.EventHorizon.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
}