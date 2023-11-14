package com.EventHorizon.EventHorizon.services;

import com.EventHorizon.EventHorizon.Exceptions.NotFoundException;
import com.EventHorizon.EventHorizon.entity.Information;
import com.EventHorizon.EventHorizon.entity.Organizer;
import com.EventHorizon.EventHorizon.entity.Sponsor;
import com.EventHorizon.EventHorizon.repository.OrganizerRepository;
import com.EventHorizon.EventHorizon.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SponsorService {

    @Autowired
    SponsorRepository sponsorRepository;
    @Autowired
    InformationService informationService;

    public void add(Sponsor sponsor) {
        try {
            sponsorRepository.save(sponsor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            Optional<Sponsor> sponsor = sponsorRepository.findById(id);
            if (sponsor.isPresent()) {
                sponsorRepository.deleteById(id);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void update(int id, Sponsor newOne) {
        try {
            Optional<Sponsor> old = sponsorRepository.findById(id);
            if (old.isPresent()) {
                Sponsor oldOne = old.get();
                informationService.update(oldOne.getInformation().getId(), newOne.getInformation());
                newOne.setId(oldOne.getId());
                sponsorRepository.save(newOne);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Sponsor getByID(int id) {
        try {
            Optional<Sponsor> sponsor = sponsorRepository.findById(id);
            if (sponsor.isPresent()) {
                return sponsor.orElse(null);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Sponsor getByInformation(Information information) {
        try {
            Optional<Sponsor> sponsor = Optional.of(sponsorRepository.findByInformation(information));
            if (sponsor.isPresent()) {
                return sponsor.orElse(null);
            } else {
                throw new NotFoundException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}