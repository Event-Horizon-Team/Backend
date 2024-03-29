package com.EventHorizon.EventHorizon.Mappers.DetailedEventDtos;

import com.EventHorizon.EventHorizon.DTOs.EventDto.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.DetailedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventRelated.AdsOptionDto;
import com.EventHorizon.EventHorizon.DTOs.UserDto.OrganizerHeaderDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;;;
import com.EventHorizon.EventHorizon.Mappers.AdsOptionDtoMapper;
import com.EventHorizon.EventHorizon.Mappers.SeatTypeListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailedDraftedEventDtoMapper implements DetailedEventDtoMapper {
    @Autowired
    private AdsOptionDtoMapper adsOptionDtoMapper;
    @Autowired
    private SeatTypeListMapper seatTypeListMapper;

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
                .seatTypes(seatTypeListMapper.getSeatTypeListFromSeatTypeListDTO(dto.getSeatTypes()))
                .build();
    }

    public DetailedDraftedEventDto getDTOfromDetailedEvent(Event event) {

        return DetailedDraftedEventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventCategory(event.getEventCategory())
                .eventDate(event.getEventDate())
                .eventLocation(event.getEventLocation())
                .eventAds(new AdsOptionDto(event.getEventAds()))
                .eventType(event.getEventType())
                .eventOrganizer(new OrganizerHeaderDto(event.getEventOrganizer()))
                .seatTypes(seatTypeListMapper.getSeatTypeDtoListFromSeatTypeList(event.getSeatTypes()))
                .build();
    }

    public void updateEventFromDetailedEventDTO(Event event, DetailedEventDto dto) {
        DetailedDraftedEventDto detailedDraftedEventDto = (DetailedDraftedEventDto)dto;
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setEventCategory(dto.getEventCategory());
        event.setEventDate(dto.getEventDate());
        event.setEventAds(this.adsOptionDtoMapper.getAdsOptionFromDTO(dto.getEventAds()));
        event.setEventLocation(dto.getEventLocation());
        event.setSeatTypes(seatTypeListMapper.getSeatTypeListFromSeatTypeListDTO(dto.getSeatTypes()));
    }
}
