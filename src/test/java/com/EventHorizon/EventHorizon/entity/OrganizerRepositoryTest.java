package com.EventHorizon.EventHorizon.entity;

import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.OrganizerNotFoundException;
import com.EventHorizon.EventHorizon.Services.InformationService;
import com.EventHorizon.EventHorizon.Services.InformationServiceComponent.OrganizerInformationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrganizerRepositoryTest {
    @Autowired
    private OrganizerInformationService organizerInformationService;

    @Autowired
    InformationCreator informationCreator;

    @Test
    public void addOrganizerTest() {
        Information information = informationCreator.getInformation("ROLE_ORGANIZER");
        organizerInformationService.add(information);
        Organizer o1 = (Organizer) organizerInformationService.getUserByInformation(information);
        Assertions.assertTrue(information.equals(o1.getInformation()));
    }


    @Test
    public void deleteOrganizerTest() {
        Information information = informationCreator.getInformation("ROLE_ORGANIZER");
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
        Information information = informationCreator.getInformation("ROLE_ORGANIZER");
        organizerInformationService.add(information);
        Organizer o1 = (Organizer) organizerInformationService.getUserByInformation(information);
        Assertions.assertEquals(o1.getInformation(), information);
    }
}