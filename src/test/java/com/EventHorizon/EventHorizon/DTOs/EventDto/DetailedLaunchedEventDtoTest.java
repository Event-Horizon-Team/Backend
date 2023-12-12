package com.EventHorizon.EventHorizon.DTOs.EventDto;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedLaunchedEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Location;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.EntityCustomCreators.InformationCustomCreator;
import com.EventHorizon.EventHorizon.Repository.OrganizerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DetailedLaunchedEventDtoTest {
    @Autowired
    private InformationCustomCreator informationCreator;
    @Autowired
    private OrganizerRepository organizerRepository;

    private Organizer tempOrganizer;
    private AdsOption tempAdsOption;
    private Location tempLocation;
    private LaunchedEvent tempEvent;
    @Test
    public void testEventDetailsDtoConstructorMapsValuesCorrectly() {
        insialize();
        DetailedLaunchedEventDto detailedLaunchedEventDto = new DetailedLaunchedEventDto(tempEvent);
        // Verify that the values are mapped correctly
        Assertions.assertEquals(tempEvent.getName(), detailedLaunchedEventDto.getName());
        Assertions.assertEquals(tempEvent.getDescription(), detailedLaunchedEventDto.getDescription());
        Assertions.assertEquals(tempEvent.getEventCategory(), detailedLaunchedEventDto.getEventCategory());
        Assertions.assertEquals(tempEvent.getEventDate(), detailedLaunchedEventDto.getEventDate());
        Assertions.assertEquals(tempEvent.getEventLocation(), detailedLaunchedEventDto.getEventLocation());
        Assertions.assertEquals(tempEvent.getLaunchedDate(), detailedLaunchedEventDto.getLaunchedDate());
    }
    private void insialize() {
        createOrganizer();
        createAdsOption();
        createLocation();
        createEvent();
    }

    private void createOrganizer() {
        Information information = informationCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        tempOrganizer = organizer;
    }

    public void createAdsOption() {
        tempAdsOption = AdsOption.builder()
                .name("p")
                .priority(2)
                .build();

    }

    private void createEvent() {
        tempEvent = LaunchedEvent.builder()
                .eventAds(tempAdsOption)
                .eventLocation(tempLocation)
                .name("EventDetailsDtoTest")
                .description("...")
                .eventOrganizer(tempOrganizer)
                .eventCategory("Category1")
                .eventDate(new Date())
                .build();
    }

    private void createLocation() {
        tempLocation = Location.builder()
                .country("Egypt")
                .city("Alex").build();
    }

}