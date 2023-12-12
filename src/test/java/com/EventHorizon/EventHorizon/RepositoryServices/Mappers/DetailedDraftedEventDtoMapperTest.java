package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.AdsOptionDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedDraftedEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DetailedDraftedEventDtoMapperTest {
    @Mock
    private AdsOptionDtoMapper adsOptionDtoMapper;

    @InjectMocks
    private DetailedDraftedEventDtoMapper detailedDraftedEventDtoMapper;


    @Test
    public void testGetEventFromDetailedEventDTO() {

        DetailedDraftedEventDto dto = new DetailedDraftedEventDto();
        dto.setId(1);
        dto.setName("Test Event");
        dto.setDescription("Test Description");
        dto.setEventCategory("Test Category");
        dto.setEventAds(new AdsOptionDto());

        AdsOption adsOption = new AdsOption();
        Mockito.when(adsOptionDtoMapper.getAdsOptionFromDTO(dto.getEventAds())).thenReturn(adsOption);

        DraftedEvent result = detailedDraftedEventDtoMapper.getEventFromDetailedEventDTO(dto);

        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getDescription(), result.getDescription());
        Assertions.assertEquals(dto.getEventCategory(), result.getEventCategory());
        Assertions.assertEquals(dto.getEventDate(), result.getEventDate());
        Assertions.assertEquals(adsOption, result.getEventAds());
        Assertions.assertEquals(dto.getEventLocation(), result.getEventLocation());
    }
    @Test
    public void testGetDTOfromDetailedEvent() {

        DraftedEvent event = new DraftedEvent();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("Test Description");
        event.setEventCategory("Test Category");
        event.setEventAds(new AdsOption());
        Organizer organizer = new Organizer();
        organizer.setId(1);
        event.setEventOrganizer(organizer);
        Information information=new Information();
        information.setId(1);
        information.setUserName("ahmed");
        organizer.setInformation(information);

        DetailedDraftedEventDto result = detailedDraftedEventDtoMapper.getDTOfromDetailedEvent(event);

        Assertions.assertEquals(event.getId(), result.getId());
        Assertions.assertEquals(event.getName(), result.getName());
        Assertions.assertEquals(event.getDescription(), result.getDescription());
        Assertions.assertEquals(event.getEventCategory(), result.getEventCategory());
        Assertions.assertEquals(event.getEventDate(), result.getEventDate());
        Assertions.assertEquals(event.getEventAds().getId(), result.getEventAds().getId());
        Assertions.assertEquals(event.getEventLocation(), result.getEventLocation());
        Assertions.assertEquals(organizer.getId(), result.getEventOrganizer().getId());
        Assertions.assertEquals(organizer.getInformation().userName, result.getEventOrganizer().getName());
    }

}