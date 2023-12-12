package com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventDtoType;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class EventDto
{
    public int id;
    public String name;
    public String description;
    public String eventCategory;
    public Date eventDate;
    public Location eventLocation;
    public OrganizerHeaderDto eventOrganizer;
}
