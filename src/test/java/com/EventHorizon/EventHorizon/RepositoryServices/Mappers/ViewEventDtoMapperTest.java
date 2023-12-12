package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.SimpleEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.AdsOption;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.LaunchedEventRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ViewEventDtoMapperTest {
    @Mock
    private AdsOptionDtoMapper adsOptionDtoMapper;

    @Mock
    private LaunchedEventRepositoryService eventRepositoryService;

    @InjectMocks
    private ViewEventDtoMapper eventDtoMapper;


    @Test
    public void testGetEventFromViewEventDTO() {

        SimpleEventDto dto = new SimpleEventDto();
        dto.setId(1);
        dto.setName("Test Event");
        dto.setDescription("Test Description");
        dto.setEventCategory("Test Category");

        LaunchedEvent event = new LaunchedEvent();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setEventCategory(dto.getEventCategory());
        event.setEventDate(dto.getEventDate());
        event.setEventLocation(dto.getEventLocation());

        AdsOption adsOption = new AdsOption();
        event.setEventAds(adsOption);
        Mockito.when(eventRepositoryService.getEventAndHandleNotFound(dto.getId())).thenReturn(event);

        LaunchedEvent result = eventDtoMapper.getEventFromViewEventDTO(dto);

        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getDescription(), result.getDescription());
        Assertions.assertEquals(dto.getEventCategory(), result.getEventCategory());
        Assertions.assertEquals(dto.getEventDate(), result.getEventDate());
        Assertions.assertEquals(dto.getEventLocation(), result.getEventLocation());
        Assertions.assertEquals(adsOption, result.getEventAds());
    }

    @Test
    public void testGetDTOfromViewEvent() {

        LaunchedEvent event = new LaunchedEvent();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("Test Description");
        event.setEventCategory("Test Category");

        Organizer organizer = new Organizer();
        organizer.setId(1);
        Information information=new Information();
        information.setId(1);
        information.setUserName("ahmed");
        organizer.setInformation(information);
        event.setEventOrganizer(organizer);
        
        SimpleEventDto result = eventDtoMapper.getDTOfromViewEvent(event);


        Assertions.assertEquals(event.getId(), result.getId());
        Assertions.assertEquals(event.getName(), result.getName());
        Assertions.assertEquals(event.getDescription(), result.getDescription());
        Assertions.assertEquals(event.getEventCategory(), result.getEventCategory());
        Assertions.assertEquals(event.getEventDate(), result.getEventDate());
        Assertions.assertEquals(event.getEventLocation(), result.getEventLocation());
        Assertions.assertEquals(organizer.getId(), result.getEventOrganizer().getId());
        Assertions.assertEquals(organizer.getInformation().userName, result.getEventOrganizer().getName());
    }

}