package com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServices;

import com.EventHorizon.EventHorizon.Entities.EventEntities.DraftedEvent;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventAlreadyExisting;
import com.EventHorizon.EventHorizon.Exceptions.EventExceptions.EventNotFoundException;
import com.EventHorizon.EventHorizon.Repository.EventRepositories.DraftedEventRepository;
import com.EventHorizon.EventHorizon.RepositoryServices.SeatArchive.EventSeatArchiveRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.SeatArchive.EventSeatTypesRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DraftedEventRepositoryService implements SuperEventRepositoryService {

    @Autowired
    private DraftedEventRepository draftedEventRepository;
    @Autowired
    private EventSeatTypesRepositoryService eventSeatTypesRepositoryService;
    @Autowired
    private EventSeatArchiveRepositoryService eventSeatArchiveRepositoryService;

    public DraftedEvent saveWhenCreating(Event event) {
        DraftedEvent draftedEvent = (DraftedEvent) event;
        if (draftedEvent.getId() != 0)
            throw new EventAlreadyExisting();

        this.handleSeatArchivesAndSaveInRepository(draftedEvent);
        return draftedEvent;
    }

    public DraftedEvent update(Event event) {
        DraftedEvent draftedEvent = (DraftedEvent) event;
        int id = draftedEvent.getId();
        getByIdAndHandleNotFound(id);
        draftedEvent.setId(id);

        this.handleSeatArchivesAndSaveInRepository(draftedEvent);
        return draftedEvent;
    }

    public void delete(int id) {
        getByIdAndHandleNotFound(id);
        draftedEventRepository.deleteById(id);
    }

    public DraftedEvent getByIdAndHandleNotFound(int id) {
        Optional<DraftedEvent> optionalOldEvent = draftedEventRepository.findById(id);
        if (optionalOldEvent.isEmpty())
            throw new EventNotFoundException();
        return optionalOldEvent.get();
    }

    private void handleSeatArchivesAndSaveInRepository(DraftedEvent draftedEvent) {
        draftedEvent.setEventType(EventType.DRAFTEDEVENT);
        eventSeatTypesRepositoryService.setEventForItsSeatTypes(draftedEvent);
        draftedEventRepository.save(draftedEvent);
        eventSeatArchiveRepositoryService.setAndSaveSeatArchivesForEvent(draftedEvent);
    }
}
