package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.AdsOptionDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.DetailedLaunchedEventDto;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelaitedEventDtoMapper {
    @Autowired
    private AdsOptionDtoMapper adsOptionDtoMapper;

    public Event getEventFromDetailedEventDTO(DetailedLaunchedEventDto dto) {
        Event event = Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .eventCategory(dto.getEventCategory())
                .eventDate(dto.getEventDate())
                .eventAds(this.adsOptionDtoMapper.getAdsOptionFromDTO(dto.getEventAds()))
                .eventLocation(dto.getEventLocation())
                .build();
        return event;
    }

    public DetailedLaunchedEventDto getDTOfromDetailedEvent(Event event) {
        DetailedLaunchedEventDto detailedLaunchedEventDto = DetailedLaunchedEventDto.builder()
                .id(event.getId())
                .eventAds(new AdsOptionDto(event.getEventAds()))
                .eventOrganizer(new OrganizerHeaderDto(event.getEventOrganizer()))
                .eventDate(event.getEventDate())
                .name(event.getName())
                .eventLocation(event.getEventLocation())
                .eventCategory(event.getEventCategory())
                .description(event.getDescription())
                .build();
        return detailedLaunchedEventDto;
    }
}
