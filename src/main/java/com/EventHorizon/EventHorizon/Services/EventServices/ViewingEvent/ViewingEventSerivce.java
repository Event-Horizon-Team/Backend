package com.EventHorizon.EventHorizon.Services.EventServices.ViewingEvent;

import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventDto;
import com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes.EventPriviledge;
import com.EventHorizon.EventHorizon.DTOs.EventDto.ViewEventDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.Event;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServiceFactory;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.SuperEventRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.InformationComponent.InformationRepositoryServiceComponent.InformationRepositoryServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ViewingEventSerivce
{
    @Autowired
    InformationRepositoryService informationRepositoryService;
    @Autowired
    EventRepositoryServiceFactory eventRepositoryServiceFactory;

    public ViewEventDto viewEvent(int eventId, EventType eventType, int userInformationId)
    {
        Information information = this.informationRepositoryService.getByID(userInformationId);
        SuperEventRepositoryService superEventRepositoryService
                = eventRepositoryServiceFactory.getEventRepositoryServiceByEventType(eventType);
        Event event = superEventRepositoryService.getEventAndHandleNotFound(eventId);

        return null;
//        return checkAdminChain(information, event);
    }



//    public ViewEventDto checkOrganizerChain(Information information, Event event)
//    {
//        return null;
//    }
}
