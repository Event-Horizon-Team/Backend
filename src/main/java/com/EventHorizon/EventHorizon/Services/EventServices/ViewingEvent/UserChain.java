package com.EventHorizon.EventHorizon.Services.EventServices.ViewingEvent;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventDtoType;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventPriviledge;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.SimpleEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.InvalidAccessOfEventException;
import org.springframework.stereotype.Service;

@Service
public class UserChain
{
    public ViewEventDto getDto(Event event)
    {
        if (event.getEventType() == EventType.DRAFTEDEVENT)
            throw new InvalidAccessOfEventException();
        return new ViewEventDto
                (new SimpleEventDto((LaunchedEvent) event)
                        , EventPriviledge.VIEWONLY
                        , EventDtoType.SIMPLE);
    }
}
