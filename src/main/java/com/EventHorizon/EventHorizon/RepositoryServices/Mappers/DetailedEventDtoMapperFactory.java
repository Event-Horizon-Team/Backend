package com.EventHorizon.EventHorizon.RepositoryServices.Mappers;

import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailedEventDtoMapperFactory {
    @Autowired
    private DetailedDraftedEventDtoMapper detailedDraftedEventDtoMapper;
    @Autowired
    private DetailedLaunchedEventDtoMapper detailedLaunchedEventDtoMapper;
    public DetailedEventDtoMapper getEventDtoMapperByEventType(EventType eventType){
        if(eventType==EventType.LAUNCHEDEVENT)
            return detailedLaunchedEventDtoMapper;
        else return detailedDraftedEventDtoMapper;
    }
}
