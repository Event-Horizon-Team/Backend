package com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent;

import com.EventHorizon.EventHorizon.DTOs.UserDto.UpdateInformationDTO;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.AdminNotFoundException;
import com.EventHorizon.EventHorizon.Repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminInformationRepositoryService implements SuperUserInformationRepositoryService {

    private final InformationRepository informationRepository;

    @Override
    public Information update(UpdateInformationDTO updateInformationDTO, Information information) {
        Information newInformation = updateInformationDTO.toInformation(information);
        Optional<Information> curInformation = informationRepository.findById(information.getId());
        if (!curInformation.isPresent()) {
            throw new AdminNotFoundException();
        }
        informationRepository.save(newInformation);
        return newInformation;
    }
}
