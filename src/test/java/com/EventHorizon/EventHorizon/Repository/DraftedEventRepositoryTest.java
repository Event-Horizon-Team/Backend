package com.EventHorizon.EventHorizon.Repository;

import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.entity.InformationCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DraftedEventRepositoryTest {
    @Autowired
    private DraftedEventRepository draftedEventRepository;

    @Autowired
    private AdsOptionRepository adsOptionRepository;
    @Autowired
    private OrganizerRepository organizerRepository;
    @Autowired
    private InformationCreator informationCreator;

    private Event tempEvent;
    private AdsOption tempAdsOption;
    private Organizer tempOrganizer;

    @Test
    public void createDraftedEvent(){
        insialize();
        DraftedEvent draftedEvent=DraftedEvent.builder()
                .event(tempEvent).build();
        draftedEventRepository.save(draftedEvent);
        Assertions.assertNotEquals(0, draftedEvent.getId());
    }
    @Test
    public void findNotExistedDraftedEventById() {
        Optional<DraftedEvent> event = draftedEventRepository.findById(0);
        assertFalse(event.isPresent());
    }
    @Test
    public void findExistedDraftedEventById() {
        insialize();
        DraftedEvent draftedEvent=DraftedEvent.builder()
                .event(tempEvent).build();
        draftedEventRepository.save(draftedEvent);
        Optional<DraftedEvent> findedEvent = draftedEventRepository.findById(draftedEvent.getId());
        assertTrue(findedEvent.isPresent());
    }
    @Test
    public void createDraftedEventWithoutEvent() {
        DraftedEvent draftedEvent=DraftedEvent.builder().build();
        Assertions.assertThrows(RuntimeException.class, () -> {
            draftedEventRepository.save(draftedEvent);
        });
    }
    @Test
    public void testOneToOneRelationBetwenDraftedEventAndEventWithError() {
        insialize();
        DraftedEvent draftedEvent=DraftedEvent.builder()
                .event(tempEvent).build();
        draftedEventRepository.save(draftedEvent);
        DraftedEvent draftedEvent2=DraftedEvent.builder()
                .event(tempEvent).build();
        Assertions.assertThrows(RuntimeException.class, () -> {
            draftedEventRepository.save(draftedEvent2);
        });

    }
    @Test
    public void testOneToOneRelationBetwenDraftedEventAndEventWithOutError() {
        insialize();
        DraftedEvent draftedEvent=DraftedEvent.builder()
                .event(tempEvent).build();
        draftedEventRepository.save(draftedEvent);
        DraftedEvent draftedEvent2=DraftedEvent.builder()
                .event(tempEvent).build();
        tempEvent.setId(0);
        Assertions.assertDoesNotThrow(() -> {
            draftedEventRepository.save(draftedEvent2);
        });

    }


    private void insialize(){
        createOrganizer();
        createAdsOption();
        createEvent();
    }
    private void  createOrganizer(){
        Information information = informationCreator.getInformation(Role.ORGANIZER);
        Organizer organizer = Organizer.builder().information(information).build();
        organizerRepository.save(organizer);
        tempOrganizer=organizer;
    }
    public void createAdsOption(){
        AdsOption adsOption = AdsOption.builder()
                .name("p")
                .priority(1)
                .build();
        adsOptionRepository.save(adsOption);
        tempAdsOption=adsOption;

    }
    private void createEvent(){
        tempEvent= Event.builder()
                .name("e5")
                .eventAds(tempAdsOption)
                .eventOrganizer(tempOrganizer)
                .description("...").build();
    }

}