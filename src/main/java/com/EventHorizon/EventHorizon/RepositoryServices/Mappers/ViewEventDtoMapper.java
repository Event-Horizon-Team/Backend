package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewEventDtoMapper {
    @Autowired
    private AdsOptionDtoMapper adsOptionDtoMapper;
    @Autowired
    private EventRepositoryService eventRepositoryService;

    public LaunchedEvent getEventFromViewEventDTO(ViewEventDto dto) {
        Event event = Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .eventCategory(dto.getEventCategory())
                .eventDate(dto.getEventDate())
                .eventLocation(dto.getEventLocation())
                .eventAds(eventRepositoryService.getEventAndHandleNotFound(dto.getId()).getEventAds())
                .build();
        return LaunchedEvent.builder().event(event).id(dto.getId()).build();
    }

    public ViewEventDto getDTOfromViewEvent(LaunchedEvent event) {
        ViewEventDto viewEventDto = ViewEventDto.builder()
                .id(event.getId())
                .eventOrganizer(new OrganizerHeaderDto(event.getEventOrganizer()))
                .eventDate(event.getEventDate())
                .name(event.getName())
                .eventLocation(event.getEventLocation())
                .eventCategory(event.getEventCategory())
                .description(event.getDescription())
                .build();
        return viewEventDto;
    }
}
