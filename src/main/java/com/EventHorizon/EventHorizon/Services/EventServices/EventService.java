package com.EventHorizon.EventHorizon.Services.EventServices;

import com.EventHorizon.EventHorizon.DTOs.EventDto.DetailedEventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventHeaderDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventIsAlreadyLaunched;
import com.EventHorizon.EventHorizon.Mappers.DetailedEventDtos.DetailedEventDtoMapperInterface;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.DashboardRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServices.EventRepositoryServiceInterface;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServices.LaunchedEventRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.OrganizerInformationRepositoryService;
import com.EventHorizon.EventHorizon.Mappers.DetailedEventDtos.DetailedLaunchedEventDtoMapper;
import com.EventHorizon.EventHorizon.Mappers.ViewEventDtoMapper;
import com.EventHorizon.EventHorizon.UtilityClasses.DateFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private LaunchedEventRepositoryService launchedEventRepositoryService;
    @Autowired
    private DashboardRepositoryService dashboardRepositoryService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private InformationRepositoryService informationService;
    @Autowired
    private ViewEventDtoMapper viewEventDtoMapper;
    @Autowired
    private OrganizerInformationRepositoryService organizerInformationService;
    @Autowired
    private DetailedLaunchedEventDtoMapper detailedLaunchedEventDtoMapper;
    @Autowired
    private EventRepositoryServiceInterface eventRepositoryServiceInterface;
    @Autowired
    private DetailedEventDtoMapperInterface detailedEventDtoMapperInterface;

    public ViewEventDto getEventForUser(int eventId) {
        LaunchedEvent event = this.launchedEventRepositoryService.getById(eventId);

        return viewEventDtoMapper.getDTOfromViewEvent(event);
    }

    public DetailedEventDto getEventForOrganizer(int informationId, int eventId, EventType eventType) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);

        Event event = eventRepositoryServiceInterface.getByIdAndEventType(eventId, eventType);
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        return detailedEventDtoMapperInterface.getDTOfromDetailedEvent(event);
    }

    public List<EventHeaderDto> getEventHeadersList(int pageIndex, int pageSize) {
        return this.dashboardRepositoryService.getPage(pageIndex, pageSize);
    }


    public DetailedEventDto createEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);

        Event event = detailedEventDtoMapperInterface.getEventFromDetailedEventDTO(eventDTO);
        event.setEventOrganizer(organizer);
        // ++++++++++++++++++++++---------------------
        event.setSeatTypes(new ArrayList<>());
        eventRepositoryServiceInterface.saveWhenCreating(event);
        return detailedEventDtoMapperInterface.getDTOfromDetailedEvent(event);

    }

    public DetailedEventDto updateEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = this.getOrganizerFromInformationId(informationId);

        Event event = detailedEventDtoMapperInterface.getEventFromDetailedEventDTO(eventDTO);
        /*                                                   ++++++++-----------------                      */
        event.setSeatTypes(new ArrayList<>());
        ((LaunchedEvent) event).setLaunchedDate(DateFunctions.getCurrentDate());
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        event.setEventOrganizer(organizer);
        event = eventRepositoryServiceInterface.update(event);
        return detailedEventDtoMapperInterface.getDTOfromDetailedEvent(event);
    }

    public DetailedEventDto launchEvent(int informationId, DetailedEventDto eventDTO) {
        validateEventIsDrafted(eventDTO);
        deleteEvent(informationId, eventDTO);

        Event event = detailedEventDtoMapperInterface.getEventFromDetailedEventDTO(eventDTO);
        event.setEventType(EventType.LAUNCHEDEVENT);
        launchedEventRepositoryService.saveWhenCreating(event);
        return detailedLaunchedEventDtoMapper.getDTOfromDetailedEvent(event);
    }

    public void deleteEvent(int informationId, DetailedEventDto eventDTO) {
        Organizer organizer = getOrganizerFromInformationId(informationId);
        EventType eventType = eventDTO.getEventType();

        Event event = eventRepositoryServiceInterface.getByIdAndEventType(eventDTO.getId(), eventType);
        userEventService.checkAndHandleNotOrganizerOfEvent(organizer, event);
        eventRepositoryServiceInterface.delete(eventDTO.getId(), eventType);
    }

    public Organizer getOrganizerFromInformationId(int inforamtionID) {
        Information information = informationService.getByID(inforamtionID);
        return (Organizer) organizerInformationService.getUserByInformation(information);
    }

    public void validateEventIsDrafted(DetailedEventDto eventDTO) {
        if (eventDTO.getEventType().equals(EventType.LAUNCHEDEVENT))
            throw new EventIsAlreadyLaunched();
    }
}