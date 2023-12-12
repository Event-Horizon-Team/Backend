package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;

public interface DetailedEventDtoMapper {
    public Event getEventFromDetailedEventDTO(DetailedEventDto dto) ;

    public DetailedEventDto getDTOfromDetailedEvent(Event event);
}
