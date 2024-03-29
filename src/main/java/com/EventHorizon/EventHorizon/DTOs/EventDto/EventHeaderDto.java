package com.EventHorizon.EventHorizon.DTOs.EventDto;

import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Location;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class EventHeaderDto {
    private int id;
    private String name;
    private String eventCategory;
    private Date eventDate;
    private Location eventLocation;
    private OrganizerHeaderDto eventOrganizer;

    public EventHeaderDto(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.eventCategory = event.getEventCategory();
        this.eventDate = event.getEventDate();
        this.eventLocation = event.getEventLocation();
        this.eventOrganizer = new OrganizerHeaderDto(event.getEventOrganizer());
    }

    @Override
    public String toString() {
        return "EventHeaderDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventCategory='" + eventCategory + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocation=" + eventLocation +
                ", eventOrganizer=" + eventOrganizer +
                '}';
    }
}
