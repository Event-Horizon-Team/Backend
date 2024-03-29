package com.EventHorizon.EventHorizon.Repository.UserRepositoryTests;

import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.OrganizerNotFoundException;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.OrganizerInformationRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrganizerRepositoryTest {
    @Autowired
    private OrganizerInformationRepositoryService organizerInformationService;

    @Autowired
    InformationCustomCreator informationCustomCreator;

    @Test
    public void addOrganizerTest() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        organizerInformationService.add(information);
        Organizer o1 = (Organizer) organizerInformationService.getUserByInformation(information);
        Assertions.assertTrue(information.equals(o1.getInformation()));
    }


    @Test
    public void deleteOrganizerTest() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        organizerInformationService.add(information);
        organizerInformationService.delete(information);
        Assertions.assertThrows(
                OrganizerNotFoundException.class, () -> {
                    organizerInformationService.getUserByInformation(information);
                }
        );
    }

    @Test
    public void getByInformationOrganizerTest() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        organizerInformationService.add(information);
        Organizer o1 = (Organizer) organizerInformationService.getUserByInformation(information);
        Assertions.assertEquals(o1.getInformation(), information);
    }
}