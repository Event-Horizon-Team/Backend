package com.EventHorizon.EventHorizon.Services;

import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import org.springframework.stereotype.Service;

@Service
public class UserEventService {

    public boolean checkOrganizerOfEvent(Organizer organizer, Event event) {
        return event.getEventOrganizer().equals(organizer);
    }
}
