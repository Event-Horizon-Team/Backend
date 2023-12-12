package com.EventHorizon.EventHorizon.DTOs.EventDto;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventPriviledge;

public class ViewEventDto
{
    private EventDto event;
    private EventPriviledge priviledge;
    private EventDtoType type;

    public ViewEventDto(EventDto event, EventPriviledge priviledge, EventDtoType type)
    {
        this.event = event;
        this.priviledge = priviledge;
        this.type = type;
    }
}
