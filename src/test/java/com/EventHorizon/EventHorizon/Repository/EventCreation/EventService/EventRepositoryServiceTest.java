package com.EventHorizon.EventHorizon.Repository.EventCreation.EventService;

import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Location;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventAlreadyExisting;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventNotFoundException;
import com.EventHorizon.EventHorizon.Repository.AdsOptionRepositry;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryService;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.Repository.OrganizerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EventRepositoryServiceTest {


    @Autowired
    private EventRepositoryService eventRepositoryService;

    @Autowired
    private AdsOptionRepositry adsOptionRepositry;
    @Autowired
    private OrganizerRepository organizerRepository;
    @Autowired
    InformationCustomCreator informationCustomCreator;

    @Test
    public void testGettingExceptionOnSendingIdWhenCreating() {
        Event event = new Event();
        event.setId(5);
        event.setName("Michael's Event");

        Assertions.assertThrows(EventAlreadyExisting.class, () -> {
            eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
        });
    }

    @Test
    public void addEventNotGettingError() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);
        Location location1 = Location.builder().country("Egypt").city("Cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("e45")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        Assertions.assertDoesNotThrow(() -> {
            eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
            Assertions.assertNotEquals(0, event.getId());
        });
    }

    @Test
    public void editEventGettingErrorEventNotFoundException() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);
        Location location1 = Location.builder().country("Egypt").city("Cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("11")
                .eventOrganizer(organizer)
                .description("...")
                .build();

        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventRepositoryService.updateEventAndHandleNotFound(event);
        });
    }

    @Test
    public void editEventGettingErrorEventAlreadyExisting() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);
        Location location1 = Location.builder().country("aswan").city("cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("e45")
                .eventOrganizer(organizer)
                .description("...")
                .id(27)
                .build();

        Event otherEvent = event; otherEvent.setId(34);

        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventRepositoryService.updateEventAndHandleNotFound(otherEvent);
        });
    }

    @Test
    public void editEventwithoutError() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);
        Location location1 = Location.builder().country("qula").city("cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("e800")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();
        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
        Location location2 = Location.builder().country("mun").city("cairo").build();
        Event newEvent = Event.builder()
                .id(event.getId())
                .eventAds(adsOption)
                .eventLocation(location2)
                .name("e500")
                .eventOrganizer(organizer)
                .description("newevent")
                .seatTypes(new ArrayList<>())
                .build();

        Assertions.assertDoesNotThrow(() -> {
            eventRepositoryService.updateEventAndHandleNotFound(newEvent);
            Assertions.assertEquals(event.getId(), newEvent.getId());
        });
    }

    @Test
    public void testDeleteEventThrowsExceptionWhenEventNotFound() {
        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventRepositoryService.deleteEvent(0);
        });
    }

    @Test
    public void testDeleteEventDeletesEventSuccessfully() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);

        Location location = Location.builder().country("Egypt").city("Cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location)
                .name("EventToDelete")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);

        Assertions.assertDoesNotThrow(() -> {
            eventRepositoryService.deleteEvent(event.getId());
        });
    }

    @Test
    public void testGetEventDetailsDtoThrowsExceptionWhenEventNotFound() {
        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventRepositoryService.getViewEventDTO(0);
        });
    }

    @Test
    public void testGetEventDetailsDtoReturnsCorrectDto() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);

        Location location = Location.builder().country("Egypt").city("Cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location)
                .eventOrganizer(organizer)
                .name("EventDetailsDtoTest")
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);

        ViewEventDto eventDetailsDto = Assertions.assertDoesNotThrow(() ->
                eventRepositoryService.getViewEventDTO(event.getId())
        );

        Assertions.assertEquals(event.getName(), eventDetailsDto.getName());
        Assertions.assertEquals(event.getDescription(), eventDetailsDto.getDescription());
    }

    @Test
    public void testGetEventHeaderDtoThrowsExceptionWhenEventNotFound() {
        Assertions.assertThrows(EventNotFoundException.class, () -> {
            eventRepositoryService.getEventHeaderDto(0);
        });
    }

    @Test
    public void testGetEventHeaderDtoReturnsCorrectDto() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);

        Location location = Location.builder().country("Egypt").city("Cairo").build();
        Event event = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location)
                .name("EventHeaderDtoTest")
                .description("...")
                .eventOrganizer(organizer)
                .seatTypes(new ArrayList<>())
                .build();

        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);

        EventHeaderDto eventHeaderDto = Assertions.assertDoesNotThrow(() ->
                eventRepositoryService.getEventHeaderDto(event.getId())
        );

        Assertions.assertEquals(event.getName(), eventHeaderDto.getName());
    }

    @Test
    public void testGetAllEventsHeaderDtoReturnsCorrectList() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);

        Location location1 = Location.builder().country("Egypt").city("Cairo").build();
        Event event1 = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("Event1")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        Location location2 = Location.builder().country("USA").city("New York").build();
        Event event2 = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location2)
                .name("Event2")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event1);
        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event2);

        int pageIndex = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        List<EventHeaderDto> eventHeaderDtos = eventRepositoryService.getAllEventsHeaderDto(pageRequest);

        Assertions.assertFalse(eventHeaderDtos.isEmpty());
    }
    @Test
    public void testGetAllEventsHeaderDtoReturnsErrors() {
        Information information = informationCustomCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();
        adsOptionRepositry.save(adsOption);

        Location location1 = Location.builder().country("Egypt").city("Cairo").build();
        Event event1 = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location1)
                .name("Event1")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        Location location2 = Location.builder().country("USA").city("New York").build();
        Event event2 = Event.builder()
                .eventAds(adsOption)
                .eventLocation(location2)
                .name("Event2")
                .eventOrganizer(organizer)
                .description("...")
                .seatTypes(new ArrayList<>())
                .build();

        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event1);
        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event2);

        int pageIndex = 10;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Assertions.assertDoesNotThrow(()-> {
            List<EventHeaderDto> eventHeaderDtos = eventRepositoryService.getAllEventsHeaderDto(pageRequest);
        });
    }
}