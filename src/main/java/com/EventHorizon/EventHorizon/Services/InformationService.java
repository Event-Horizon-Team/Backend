package com.EventHorizon.EventHorizon.Services;

import com.EventHorizon.EventHorizon.DTOs.UserDto.UpdateInformationDTO;
import com.EventHorizon.EventHorizon.DTOs.UserDto.ViewInformationDTO;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.InformationNotFoundException;
import com.EventHorizon.EventHorizon.Repository.*;
import com.EventHorizon.EventHorizon.Services.InformationServiceComponent.InformationServiceFactory;
import com.EventHorizon.EventHorizon.Services.InformationServiceComponent.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformationService {
    @Autowired
    InformationRepository informationRepository;
    @Autowired
    InformationServiceFactory informationServiceFactory;

    public void add(Information information) {
        UserInformationService myService =
                informationServiceFactory.getUserInformationServiceByRole(information.getRole());
        myService.add(information);
    }

    public void delete(int id) {
        Optional<Information> informationOp = informationRepository.findById(id);
        if (informationOp.isPresent()) {
            UserInformationService myService =
                    informationServiceFactory.getUserInformationServiceByRole(informationOp.get().getRole());
            myService.delete(informationOp.get());
        } else {
            throw new InformationNotFoundException();
        }
    }


    public void update(int id, Information newOne) {
            Optional<Information> old = informationRepository.findById(id);
            if (old.isPresent()) {
                Information oldOne = old.get();
                newOne.setId(oldOne.getId());
                informationRepository.save(newOne);
            } else {
                throw new InformationNotFoundException();
            }
    }

    public Information getByID(int id) {
        Optional<Information> information = informationRepository.findById(id);
        if (information.isPresent()) {
            return information.get();
        } else {
            throw new InformationNotFoundException();
        }
    }

    public Information getByEmail(String email) {
        Optional<Information> information = Optional.ofNullable(informationRepository.findByEmail(email));
        if (information.isPresent()) {
            return information.get();
        } else {
            throw new InformationNotFoundException();
        }
    }

    public Information getByUserName(String username) {
        Optional<Information> information = Optional.ofNullable(informationRepository.findByUserName(username));
        if (information.isPresent()) {
            return information.get();
        } else {
            throw new InformationNotFoundException();
        }
    }

    public List<Information> getByLastName(String lastname) {
        List<Information> list = informationRepository.findByLastName(lastname);
        return list;
    }

    public List<Information> getByFirstName(String firstname) {
        List<Information> list = informationRepository.findByFirstName(firstname);
        return list;
    }

    public List<Information> getByGender(String gender) {
        List<Information> list = informationRepository.findByGender(gender);
        return list;
    }

    public List<Information> getByRole(String role) {
        List<Information> list = informationRepository.findByRole(role);
        return list;
    }

    public List<Information> getBySignIn(int value) {
        List<Information> list = informationRepository.findBySignInWithEmail(value);
        return list;
    }

    public ViewInformationDTO updateWithDto(UpdateInformationDTO updateInformationDTO){
        Optional<Information> informationOp = informationRepository.findById(updateInformationDTO.getId());
        if (informationOp.isPresent()) {
            UserInformationService myService = informationServiceFactory.getUserInformationServiceByRole(informationOp.get().getRole());
            return getViewInformationDTO( myService.update(updateInformationDTO,informationOp.get()));
        } else {
            throw new InformationNotFoundException();
        }
    }

    public ViewInformationDTO getViewInformationDTO(Information information) {
        UserInformationService myService = informationServiceFactory.getUserInformationServiceByRole(information.getRole());
        return new ViewInformationDTO(information);
    }
}