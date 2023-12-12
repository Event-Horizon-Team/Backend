package com.EventHorizon.EventHorizon.ServiceTests;

import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.InvalidAccessOfEventException;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.Services.UserEventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserEventServiceTest {
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private InformationCustomCreator informationCustomCreator;

    @Test
    public void organizerOfEvent() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        Event event = Event.builder()
                .eventOrganizer(organizer)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        });
    }

    @Test
    public void notOrganizerOfEvent() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        Event event = Event.builder()
                .eventOrganizer(organizer)
                .build();
        Information information2 = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer2 = Organizer.builder().information(information2).build();
        Assertions.assertThrows(InvalidAccessOfEventException.class,() -> {
            userEventService.checkAndHandleNotOrganizerOfEvent(organizer2, event);
        });
    }
}
