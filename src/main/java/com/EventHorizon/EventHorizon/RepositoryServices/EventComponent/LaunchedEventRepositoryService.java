package com.EventHorizon.EventHorizon.RepositoryServices.EventComponent;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventHeaderDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.SimpleEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventAlreadyExisting;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventNotFoundException;
import com.EventHorizon.EventHorizon.Repository.LaunchedEventRepository;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventWrapper.FutureEventWrapper;
import com.EventHorizon.EventHorizon.RepositoryServices.SeatArchive.EventSeatArchiveRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LaunchedEventRepositoryService implements SuperEventRepositoryService {
    @Autowired
    private LaunchedEventRepository launchedEventRepository;
    @Autowired
    private EventSeatArchiveRepositoryService eventSeatArchiveRepositoryService;

    public LaunchedEvent getEventAndHandleNotFound(int id) {
        Optional<LaunchedEvent> optionalLaunchedEvent = launchedEventRepository.findById(id);
        if (optionalLaunchedEvent.isEmpty())
            throw new EventNotFoundException();
        return optionalLaunchedEvent.get();
    }

    public LaunchedEvent saveEventWhenCreatingAndHandleAlreadyExisting(Event event) {
        LaunchedEvent launchedEvent = (LaunchedEvent) event;
        if (launchedEvent.getId() != 0)
            throw new EventAlreadyExisting();
        FutureEventWrapper eventWrapper = new FutureEventWrapper(launchedEvent);
        saveEvent(eventWrapper);
        return launchedEvent;
    }

    public LaunchedEvent updateEventAndHandleNotFound(Event event) {
        LaunchedEvent newEvent = (LaunchedEvent) event;
        getEventAndHandleNotFound(newEvent.getId());
        int id = newEvent.getId();
        newEvent.setId(id);
        FutureEventWrapper eventWrapper = new FutureEventWrapper(newEvent);
        saveEvent(eventWrapper);
        return newEvent;
    }

    public void deleteEvent(int id) {
        getEventAndHandleNotFound(id);
        launchedEventRepository.deleteById(id);
    }

    public SimpleEventDto getViewEventDTO(int id) {
        LaunchedEvent launchedEvent = getEventAndHandleNotFound(id);
        return new SimpleEventDto(launchedEvent);
    }

    public EventHeaderDto getEventHeaderDto(int id) {
        LaunchedEvent launchedEvent = getEventAndHandleNotFound(id);
        return new EventHeaderDto(launchedEvent);
    }

    public List<EventHeaderDto> getAllEventsHeaderDto(PageRequest pageRequest) {
        List<LaunchedEvent> events = launchedEventRepository.findAll(pageRequest).getContent();
        List<EventHeaderDto> eventHeaderDtos = new ArrayList<>();
        for (LaunchedEvent event : events) {
            eventHeaderDtos.add(new EventHeaderDto(event));
        }
        return eventHeaderDtos;
    }

    private void saveEvent(FutureEventWrapper futureEventWrapper) {
        eventSeatArchiveRepositoryService.setEventForItsSeatArchives(futureEventWrapper.getLaunchedEvent());
        launchedEventRepository.save(futureEventWrapper.getLaunchedEvent());
    }

    public List<? extends Event> getAllEvents(Specification<Event> specification) {
        return launchedEventRepository.findAll( castToLunchedEvents(specification));
    }

    public List<LaunchedEvent> getAllEvents() {
        return launchedEventRepository.findAll();
    }
    private static <T extends Event> Specification<T> castToLunchedEvents(Specification<? extends Event> obj) {
        // Warning: Unchecked cast
        return (Specification<T>) obj;
    }
}

