package com.EventHorizon.EventHorizon.RepositoryServices.EventComponent;

import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
@SpringBootTest
class EventRepositoryServiceFactoryTest {
    @Mock
    private LaunchedEventRepositoryService launchedEventRepositoryService;

    @Mock
    private DraftedEventRepositoryService draftedEventRepositoryService;

    @InjectMocks
    private EventRepositoryServiceFactory eventRepositoryServiceFactory;

    @Test
    void getEventRepositoryServiceByEventType_LaunchedEvent_ReturnsLaunchedEventRepositoryService() {
        EventType eventType = EventType.LAUNCHEDEVENT;

        SuperEventRepositoryService result = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        result.deleteEvent(0);
        verifyNoInteractions(draftedEventRepositoryService);
        verify(launchedEventRepositoryService).deleteEvent(0);

    }

    @Test
    void getEventRepositoryServiceByEventType_DraftedEvent_ReturnsDraftedEventRepositoryService() {
        EventType eventType = EventType.DRAFTEDEVENT;

        SuperEventRepositoryService result = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        result.deleteEvent(0);
        verifyNoInteractions(launchedEventRepositoryService); // Ensure the other service is not called
        verify(draftedEventRepositoryService).deleteEvent(0); // Add relevant method from DraftedEventRepositoryService

    }

}