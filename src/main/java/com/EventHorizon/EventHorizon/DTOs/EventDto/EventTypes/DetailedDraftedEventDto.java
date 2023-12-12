package com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes;

import com.EventHorizon.EventHorizon.DTOs.EventDto.AdsOptionDto;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Data
public class DetailedDraftedEventDto extends DetailedEventDto {

    public DetailedDraftedEventDto(DraftedEvent event) {
        this.setId(event.getId());
        this.setName(event.getName());
        this.setDescription(event.getDescription());
        this.setEventCategory(event.getEventCategory());
        this.setEventDate(event.getEventDate());
        this.setEventLocation(event.getEventLocation());
        this.setEventAds(new AdsOptionDto(event.getEventAds()));
        this.setEventType(event.getEventType());
        this.setEventOrganizer(new OrganizerHeaderDto(event.getEventOrganizer()));
    }
}
