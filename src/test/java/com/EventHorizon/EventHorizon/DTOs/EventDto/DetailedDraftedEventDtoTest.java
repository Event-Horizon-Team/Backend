package com.EventHorizon.EventHorizon.DTOs.EventDto;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.*;
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
class DetailedDraftedEventDtoTest {
    @Autowired
    private InformationCustomCreator informationCreator;
    @Autowired
    private OrganizerRepository organizerRepository;

    private Organizer tempOrganizer;
    private AdsOption tempAdsOption;
    private Location tempLocation;
    private DraftedEvent draftedEvent;
    @Test
    public void testEventDetailsDtoConstructorMapsValuesCorrectly() {
        insialize();
        DetailedDraftedEventDto detailedDraftedEventDto = new DetailedDraftedEventDto(draftedEvent);

        // Verify that the values are mapped correctly
        Assertions.assertEquals(draftedEvent.getName(), detailedDraftedEventDto.getName());
        Assertions.assertEquals(draftedEvent.getDescription(), detailedDraftedEventDto.getDescription());
        Assertions.assertEquals(draftedEvent.getEventCategory(), detailedDraftedEventDto.getEventCategory());
        Assertions.assertEquals(draftedEvent.getEventDate(), detailedDraftedEventDto.getEventDate());
        Assertions.assertEquals(draftedEvent.getEventLocation(), detailedDraftedEventDto.getEventLocation());
        
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
        draftedEvent = DraftedEvent.builder()
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