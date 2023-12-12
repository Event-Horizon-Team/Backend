package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.SimpleEventDto;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.LaunchedEventRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewEventDtoMapper {
    @Autowired
    private AdsOptionDtoMapper adsOptionDtoMapper;
    @Autowired
    private LaunchedEventRepositoryService launchedEventRepositoryService;

    public LaunchedEvent getEventFromViewEventDTO(SimpleEventDto dto) {
        return LaunchedEvent.builder()
                .name(dto.getName())
                .id(dto.getId())
                .description(dto.getDescription())
                .eventCategory(dto.getEventCategory())
                .eventDate(dto.getEventDate())
                .eventLocation(dto.getEventLocation())
                .eventAds(launchedEventRepositoryService.getEventAndHandleNotFound(dto.getId()).getEventAds())
                .build();
    }

    public SimpleEventDto getDTOfromViewEvent(LaunchedEvent event) {
        return SimpleEventDto.builder()
                .id(event.getId())
                .eventOrganizer(new OrganizerHeaderDto(event.getEventOrganizer()))
                .eventDate(event.getEventDate())
                .name(event.getName())
                .eventLocation(event.getEventLocation())
                .eventCategory(event.getEventCategory())
                .description(event.getDescription())
                .build();
    }
}
