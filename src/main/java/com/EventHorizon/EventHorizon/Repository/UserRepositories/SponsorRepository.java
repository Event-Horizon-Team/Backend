package com.EventHorizon.EventHorizon.Repository.UserRepositories;

import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {


    Sponsor findByInformation(Information information);

    @Override
    List<Sponsor> findAll();
}