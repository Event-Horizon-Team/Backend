package com.EventHorizon.EventHorizon.EventCreation.EventCreationService;

import com.EventHorizon.EventHorizon.Entities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.Event;
import com.EventHorizon.EventHorizon.Entities.EventCreationRepository;
import com.EventHorizon.EventHorizon.Entities.Location;
import com.EventHorizon.EventHorizon.Exceptions.EventAlreadyExisting;
import com.EventHorizon.EventHorizon.Repository.AdsOptionRepositry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventCreationRepository eventCreationRepository;
    @Autowired
    private AdsOptionRepositry adsOptionRepositry;

    @Test
    public void addEvent(){
        AdsOption adsOption =AdsOption.builder().name("p").priority(2).build();
        adsOptionRepositry.save(adsOption);
        Location location1= Location.builder().country("Egypt").city("cairo").build();
        Event event=Event.builder().eventAds(adsOption).eventLocation(location1).name("e45").description("...").build();
        eventCreationRepository.saveEventWhenCreating(event);
    }
    @Test
    public void editEvent(){
        AdsOption adsOption =AdsOption.builder().name("p").priority(2).build();
        adsOptionRepositry.save(adsOption);
        Location location1= Location.builder().country("Egypt").city("cairo").build();
        Event event=Event.builder().eventAds(adsOption).eventLocation(location1).name("11").description("...").build();
        eventCreationRepository.updateEvent(24,event);
    }

    @Test
    public void editEvent2(){
        AdsOption adsOption =AdsOption.builder().name("p").priority(2).build();
        adsOptionRepositry.save(adsOption);
        Location location1= Location.builder().country("aswan").city("cairo").build();
        Event event=Event.builder().eventAds(adsOption).eventLocation(location1).name("e45").description("...").id(27).build();
        eventCreationRepository.saveEventWhenCreating(event);
    }
    @Test
    public void editEvent3(){
        AdsOption adsOption =AdsOption.builder().name("p").priority(2).build();
        adsOptionRepositry.save(adsOption);
        Location location1= Location.builder().country("Egypt").city("cairo").build();
        Event event=Event.builder().eventAds(adsOption).eventLocation(location1).name("11").description("...").build();
        eventCreationRepository.updateEvent(30,event);
    }

    @Test
    public void testGettingExceptionOnSendingIdWhenCreating()
    {
        Event event = new Event();
        event.setId(5);
        event.setName("Michael's Event");

        try {
            this.eventCreationRepository.saveEventWhenCreating(event);
            Assertions.fail();
        }
        catch (EventAlreadyExisting e)
        {
        }
    }
}