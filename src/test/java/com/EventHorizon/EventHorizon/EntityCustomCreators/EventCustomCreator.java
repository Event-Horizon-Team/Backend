package com.EventHorizon.EventHorizon.EntityCustomCreators;

import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Repository.AdsOptionRepository;
import com.EventHorizon.EventHorizon.Repository.EventRepositry;
import com.EventHorizon.EventHorizon.Repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class EventCustomCreator
{
    @Autowired
    private AdsOptionCustomCreator adsOptionCustomCreator;
    @Autowired
    private UserCustomCreator userCustomCreator;
    @Autowired
    private LocationCustomCreator locationCustomCreator;
    @Autowired
    private EventRepositry eventRepositry;
    @Autowired
    private OrganizerRepository organizerRepository;
    @Autowired
    private AdsOptionRepository adsOptionRepositry;

    int numberOfcreatedEvents = 0;

    public LaunchedEvent getLaunchedEvent() {
        this.numberOfcreatedEvents++;

        Organizer customOrganizer = (Organizer) this.userCustomCreator.getUserAndSaveInRepository(Role.ORGANIZER);
        organizerRepository.save(customOrganizer);
        AdsOption customAdsOption = this.adsOptionCustomCreator.getAdsOption();
        this.adsOptionRepositry.save(customAdsOption);

        LaunchedEvent event = LaunchedEvent.builder()
                .eventAds(customAdsOption)
                .eventLocation(locationCustomCreator.getLocation())
                .name("Event" + numberOfcreatedEvents)
                .eventOrganizer(customOrganizer)
                .description("description" + numberOfcreatedEvents)
                .seatTypes(new ArrayList<>())
                .eventDate(new Date(System.currentTimeMillis()+100000))
                .build();
        return event;
    }

    public DraftedEvent getDraftedEvent() {
        this.numberOfcreatedEvents++;

        Organizer customOrganizer = (Organizer) this.userCustomCreator.getUserAndSaveInRepository(Role.ORGANIZER);
        organizerRepository.save(customOrganizer);
        AdsOption customAdsOption = this.adsOptionCustomCreator.getAdsOption();
        this.adsOptionRepositry.save(customAdsOption);

        DraftedEvent event = DraftedEvent.builder()
                .eventAds(customAdsOption)
                .eventLocation(locationCustomCreator.getLocation())
                .name("Event" + numberOfcreatedEvents)
                .eventOrganizer(customOrganizer)
                .description("description" + numberOfcreatedEvents)
                .seatTypes(new ArrayList<>())
                .eventDate(new Date(System.currentTimeMillis()+100000))
                .build();
        return event;
    }
    public LaunchedEvent getLaunchedEvent(Organizer organizer) {
        this.numberOfcreatedEvents++;
        AdsOption customAdsOption = this.adsOptionCustomCreator.getAdsOption();
        this.adsOptionRepositry.save(customAdsOption);

        LaunchedEvent event = LaunchedEvent.builder()
                .eventAds(customAdsOption)
                .eventLocation(locationCustomCreator.getLocation())
                .name("Event" + numberOfcreatedEvents)
                .eventOrganizer(organizer)
                .description("description" + numberOfcreatedEvents)
                .seatTypes(new ArrayList<>())
                .eventDate(new Date(System.currentTimeMillis()+100000))
                .build();
        return event;
    }

}
