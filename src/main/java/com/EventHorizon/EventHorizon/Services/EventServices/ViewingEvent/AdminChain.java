package com.EventHorizon.EventHorizon.Services.EventServices.ViewingEvent;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventDtoType;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedLaunchedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventPriviledge;
import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.InvalidAccessOfEventException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminChain
{
    @Autowired
    OrganizerChain organizerChain;

    public ViewEventDto getDto(Information information, Event event)
    {
        if (information.getRole() != Role.ADMIN)
            return organizerChain.getDto(information, event);

        if (event.getEventType() == EventType.DRAFTEDEVENT)
            throw new InvalidAccessOfEventException();
        return new ViewEventDto
                (new DetailedLaunchedEventDto((LaunchedEvent) event)
                        , EventPriviledge.ADMIN
                        , EventDtoType.DETAILEDLAUNCHED);
    }
}
