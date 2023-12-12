package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailedDraftedEventDtoMapper implements DetailedEventDtoMapper {
    @Autowired
    private AdsOptionDtoMapper adsOptionDtoMapper;

    public DraftedEvent getEventFromDetailedEventDTO(DetailedEventDto dto) {
        DetailedDraftedEventDto detailedDraftedEventDto=(DetailedDraftedEventDto)dto;
        return DraftedEvent.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .eventCategory(dto.getEventCategory())
                .eventDate(dto.getEventDate())
                .eventAds(this.adsOptionDtoMapper.getAdsOptionFromDTO(dto.getEventAds()))
                .eventLocation(dto.getEventLocation())
                .build();
    }

    public DetailedDraftedEventDto getDTOfromDetailedEvent(Event event) {
        DraftedEvent draftedEvent=(DraftedEvent)event;
        DetailedDraftedEventDto detailedDraftedEventDto = new DetailedDraftedEventDto(draftedEvent);
        return detailedDraftedEventDto;
    }
}
