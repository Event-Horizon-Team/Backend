package com.EventHorizon.EventHorizon.Services.EventServices.ViewingEvent;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventDtoType;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedLaunchedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventPriviledge;
import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.InvalidAccessOfEventException;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.OrganizerInformationRepositoryService;
import com.EventHorizon.EventHorizon.Services.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizerChain
{
    @Autowired
    UserEventService userEventService;
    @Autowired
    OrganizerInformationRepositoryService organizerInformationRepositoryService;

    public ViewEventDto getDto(Information information, Event event) {
        if (information.getRole() != Role.ORGANIZER)
            throw new InvalidAccessOfEventException();
        Organizer organizer
                = (Organizer)organizerInformationRepositoryService.getUserByInformation(information);

        this.userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);

        if (event.getEventType() == EventType.DRAFTEDEVENT)
            return this.getLaunchedDto(event);

        return this.getDraftedDto(event);
    }

    private ViewEventDto getLaunchedDto(Event event) {
        return new ViewEventDto
                (new DetailedDraftedEventDto((DraftedEvent) event)
                        , EventPriviledge.ADMIN
                        , EventDtoType.DETAILEDDRAFTED);
    }

    private ViewEventDto getDraftedDto(Event event) {
        return new ViewEventDto
                (new DetailedDraftedEventDto((DraftedEvent) event)
                        , EventPriviledge.ADMIN
                        , EventDtoType.DETAILEDDRAFTED);
    }
}
