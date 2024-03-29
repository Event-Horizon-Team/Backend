package com.EventHorizon.EventHorizon.Repository.UserRepositoryTests;

import com.EventHorizon.EventHorizon.Entities.UserEntities.Client;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.Exceptions.UsersExceptions.ClientNotFoundException;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.ClientInformationRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientRepositoryTest {
    @Autowired
    private ClientInformationRepositoryService clientInformationService;

    @Autowired
    InformationCustomCreator informationCustomCreator;

    @Test
    public void addClientTest() {
        Information information = informationCustomCreator.getInformation(Role.CLIENT);
        clientInformationService.add(information);
        Client c1 = (Client) clientInformationService.getUserByInformation(information);
        Assertions.assertTrue(information.equals(c1.getInformation()));
    }

    @Test
    public void deleteClientTest() {
        Information information = informationCustomCreator.getInformation(Role.CLIENT);
        clientInformationService.add(information);
        clientInformationService.delete(information);
        Assertions.assertThrows(
                ClientNotFoundException.class, () -> {
                    clientInformationService.getUserByInformation(information);
                }
        );
    }
    @Test
    public void getByInformationClientTest() {
        Information information = informationCustomCreator.getInformation(Role.CLIENT);
        clientInformationService.add(information);
        Client c1 = (Client) clientInformationService.getUserByInformation(information);
        Assertions.assertEquals(c1.getInformation(), information);
    }

}