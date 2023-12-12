package com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes;

import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SimpleEventDto extends EventDto
{
    public SimpleEventDto(LaunchedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventCategory = event.getEventCategory();
        this.eventDate = event.getEventDate();
        this.description = event.getDescription();
        this.eventLocation = event.getEventLocation();
        this.eventDtoType = EventDtoType.SIMPLE;
        this.eventOrganizer = new OrganizerHeaderDto(event.getEventOrganizer());
    }
}
