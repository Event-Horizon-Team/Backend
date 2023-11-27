package com.EventHorizon.EventHorizon.Services;

import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.ModeratorNotFoundException;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Moderator;
import com.EventHorizon.EventHorizon.Repository.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModeratorService {
    @Autowired
    ModeratorRepository moderatorRepository;
    @Autowired
    InformationService informationService;

    public void add(Moderator moderator) {
            moderatorRepository.save(moderator);
    }

    public void delete(int id) {
        Optional<Moderator> moderator = moderatorRepository.findById(id);
        if (moderator.isPresent()) {
            moderatorRepository.deleteById(id);
        } else {
            throw new ModeratorNotFoundException();
        }
    }


    public void update(int id, Moderator newOne) {
        Optional<Moderator> old = moderatorRepository.findById(id);
        if (old.isPresent()) {
            Moderator oldOne = old.get();
            informationService.update(oldOne.getInformation().getId(), newOne.getInformation());
            newOne.setId(oldOne.getId());
            moderatorRepository.save(newOne);
        } else {
            throw new ModeratorNotFoundException();
        }
    }

    public Moderator getByID(int id) {
        Optional<Moderator> moderator = moderatorRepository.findById(id);
        if (moderator.isPresent()) {
            return moderator.get();
        } else {
            throw new ModeratorNotFoundException();
        }
    }

    public Moderator getByInformation(Information information) {
        Optional<Moderator> moderator = Optional.of(moderatorRepository.findByInformation(information));
        if (moderator.isPresent()) {
            return moderator.get();
        } else {
            throw new ModeratorNotFoundException();
        }
    }
}