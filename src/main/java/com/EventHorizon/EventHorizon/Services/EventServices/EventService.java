package com.EventHorizon.EventHorizon.Services.EventServices;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.DetailedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventHeaderDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.SimpleEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventIsAlreadyLaunched;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.DashboardRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServiceFactory;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.LaunchedEventRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.SuperEventRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.OrganizerInformationRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.Mappers.DetailedEventDtoMapper;
import com.EventHorizon.EventHorizon.RepositoryServices.Mappers.DetailedEventDtoMapperFactory;
import com.EventHorizon.EventHorizon.RepositoryServices.Mappers.DetailedLaunchedEventDtoMapper;
import com.EventHorizon.EventHorizon.RepositoryServices.Mappers.ViewEventDtoMapper;
import com.EventHorizon.EventHorizon.Services.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepositoryServiceFactory eventRepositoryServiceFactory;
    @Autowired
    private LaunchedEventRepositoryService launchedEventRepositoryService;
    @Autowired
    private DetailedEventDtoMapperFactory detailedEventDtoMapperFactory;
    @Autowired
    private DashboardRepositoryService dashboardRepositoryService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private InformationRepositoryService informationRepositoryService;
    @Autowired
    private ViewEventDtoMapper viewEventDtoMapper;
    @Autowired
    private OrganizerInformationRepositoryService organizerInformationService;
    @Autowired
    private DetailedLaunchedEventDtoMapper detailedLaunchedEventDtoMapper;

    public SimpleEventDto getEventForUser(int eventId) {
        LaunchedEvent event = this.launchedEventRepositoryService.getEventAndHandleNotFound(eventId);

        return viewEventDtoMapper.getDTOfromViewEvent(event);
    }

    public DetailedEventDto getEventForOrganizer(int informationId, int eventId, EventType eventType) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);
        DetailedEventDtoMapper detailedEventDtoMapper = detailedEventDtoMapperFactory.getEventDtoMapperByEventType(eventType);
        SuperEventRepositoryService eventRepositoryService = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        Event event = eventRepositoryService.getEventAndHandleNotFound(eventId);
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        return detailedEventDtoMapper.getDTOfromDetailedEvent(event);
    }

    public List<EventHeaderDto> getEventHeadersList(int pageIndex, int pageSize) {
        return this.dashboardRepositoryService.getPage(pageIndex, pageSize);
    }

    public DetailedEventDto createEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);
        EventType eventType = eventDTO.getEventType();
        DetailedEventDtoMapper detailedEventDtoMapper = detailedEventDtoMapperFactory.getEventDtoMapperByEventType(eventType);
        SuperEventRepositoryService eventRepositoryService = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        Event event = detailedEventDtoMapper.getEventFromDetailedEventDTO(eventDTO);
        event.setEventOrganizer(organizer);
        eventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
        return detailedEventDtoMapper.getDTOfromDetailedEvent(event);

    }

    public DetailedEventDto updateEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);
        EventType eventType = eventDTO.getEventType();
        DetailedEventDtoMapper detailedEventDtoMapper = detailedEventDtoMapperFactory.getEventDtoMapperByEventType(eventType);
        SuperEventRepositoryService eventRepositoryService = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        Event event = detailedEventDtoMapper.getEventFromDetailedEventDTO(eventDTO);
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        event = eventRepositoryService.updateEventAndHandleNotFound(event);
        return detailedEventDtoMapper.getDTOfromDetailedEvent(event);
    }

    public DetailedEventDto launchEvent(int informationId, DetailedEventDto eventDTO) {
        validateEventIsDrafted(eventDTO);
        deleteEvent(informationId, eventDTO);
        EventType eventType = eventDTO.getEventType();
        DetailedEventDtoMapper detailedEventDtoMapper = detailedEventDtoMapperFactory.getEventDtoMapperByEventType(eventType);
        Event event = detailedEventDtoMapper.getEventFromDetailedEventDTO(eventDTO);
        event.setEventType(EventType.LAUNCHEDEVENT);
        launchedEventRepositoryService.saveEventWhenCreatingAndHandleAlreadyExisting(event);
        return detailedLaunchedEventDtoMapper.getDTOfromDetailedEvent(event);
    }

    public void deleteEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = getOrganizerFromInformationId(informationId);
        EventType eventType = eventDTO.getEventType();
        SuperEventRepositoryService eventRepositoryService = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        Event event = eventRepositoryService.getEventAndHandleNotFound(eventDTO.getId());
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        eventRepositoryService.deleteEvent(eventDTO.getId());
    }

    public Organizer getOrganizerFromInformationId(int inforamtionID) {
        Information information = informationRepositoryService.getByID(inforamtionID);
        return (Organizer) organizerInformationService.getUserByInformation(information);
    }

    public void validateEventIsDrafted(DetailedEventDto eventDTO) {
        if (eventDTO.getEventType().equals(EventType.LAUNCHEDEVENT))
            throw new EventIsAlreadyLaunched();
    }
}