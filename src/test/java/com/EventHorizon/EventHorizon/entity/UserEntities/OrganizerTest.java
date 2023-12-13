package com.EventHorizon.EventHorizon.entity.UserEntities;

import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.EntityCustomCreators.EventCustomCreator;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.EntityCustomCreators.UserCustomCreator;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.LaunchedEventRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.OrganizerInformationRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrganizerTest {

    @Autowired
    EventCustomCreator eventCustomCreator;
    @Autowired
    LaunchedEventRepositoryService launchedEventRepositoryService;

    @Autowired
    UserCustomCreator userCustomCreator;

    @Autowired
    private OrganizerInformationRepositoryService organizerInformationService;


    @Test
    public void getMyEvents() {
        Organizer organizer = (Organizer) userCustomCreator.getUserAndSaveInRepository(Role.ORGANIZER);

        Event event = eventCustomCreator.getLaunchedEvent(organizer);
        launchedEventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
        Event event2 = eventCustomCreator.getLaunchedEvent(organizer);
        launchedEventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event2);


        Organizer organizer2 = (Organizer) organizerInformationService.getUserByInformation(organizer.getInformation());


        List<Event> myEvents = organizer2.getMyEvents();

        Assertions.assertEquals(myEvents.size(), 2);
    }
}
