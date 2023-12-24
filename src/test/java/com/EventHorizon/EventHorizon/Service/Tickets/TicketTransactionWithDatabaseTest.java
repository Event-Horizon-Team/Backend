package com.EventHorizon.EventHorizon.Service.Tickets;

import com.EventHorizon.EventHorizon.DTOs.TicketDto.BuyingAndRefundingDto;
import com.EventHorizon.EventHorizon.Entities.EventEntities.LaunchedEvent;
import com.EventHorizon.EventHorizon.Entities.SeatArchive.OrganizerSeatArchive;
import com.EventHorizon.EventHorizon.Entities.SeatArchive.SeatType;
import com.EventHorizon.EventHorizon.Entities.Tickets.BuyedTicketCollection;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Client;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.EntityCustomCreators.EventCustomCreator;
import com.EventHorizon.EventHorizon.EntityCustomCreators.SeatTypeCustomCreator;
import com.EventHorizon.EventHorizon.EntityCustomCreators.UserCustomCreator;
import com.EventHorizon.EventHorizon.RepositoryServices.EventComponent.EventRepositoryServices.EventRepositoryServiceInterface;
import com.EventHorizon.EventHorizon.RepositoryServices.SeatArchive.OrganizerSeatArchiveRepositoryService;
import com.EventHorizon.EventHorizon.RepositoryServices.Tickets.BuyedTicketCollectionRepositoryService;
import com.EventHorizon.EventHorizon.Services.Tickets.TicketTransactionsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TicketTransactionWithDatabaseTest
{
    @Autowired
    private TicketTransactionsService ticketTransactionService;
    @Autowired
    private EventCustomCreator eventCustomCreator;
    @Autowired
    private SeatTypeCustomCreator seatTypeCustomCreator;
    @Autowired
    private EventRepositoryServiceInterface eventRepositoryServiceInterface;
    @Autowired
    private OrganizerSeatArchiveRepositoryService organizerSeatArchiveRepositoryService;
    @Autowired
    private UserCustomCreator userCustomCreator;
    @Autowired
    private BuyedTicketCollectionRepositoryService buyedTicketCollectionRepositoryService;


    private BuyedTicketCollection customTicketCollection;
    private Client customClient;
    private LaunchedEvent customEvent;
    private SeatType customSeatType;
    private OrganizerSeatArchive customOrganizerSeatArchive;


    @Test
    public void buyOneTicketSuccessful(){
        this.initializeCustomObjects();
        BuyingAndRefundingDto buyingAndRefundingDto = new BuyingAndRefundingDto(this.customSeatType.getId(), 1);
        Assertions.assertDoesNotThrow(() ->
                this.ticketTransactionService
                        .buyTicketCollections(
                                this.customClient.getInformation().getId(), List.of(buyingAndRefundingDto, buyingAndRefundingDto)) );
        OrganizerSeatArchive organizerSeatArchive = this.organizerSeatArchiveRepositoryService.getBySeatTypeId(this.customOrganizerSeatArchive.getSeatTypeId());
        Assertions.assertEquals(0, organizerSeatArchive.getAvailable_number_of_seats());

        BuyedTicketCollection buyedTicketCollection = this.buyedTicketCollectionRepositoryService.getBySeatTypeIdAndClientId(this.customSeatType.getId(), this.customClient.getId());
        Assertions.assertEquals(2, buyedTicketCollection.getNumberOfTickets());
    }

    @Test
    public void buyOneTicketsMoreThanArchiveHas(){
        this.initializeCustomObjects();
        BuyingAndRefundingDto buyingAndRefundingDto = new BuyingAndRefundingDto(this.customSeatType.getId(), 1);
        Assertions.assertThrows(RuntimeException.class, () ->
                this.ticketTransactionService
                        .buyTicketCollections(
                                this.customClient.getInformation().getId()
                                , List.of(buyingAndRefundingDto, buyingAndRefundingDto, buyingAndRefundingDto)) );
        OrganizerSeatArchive organizerSeatArchive = this.organizerSeatArchiveRepositoryService.getBySeatTypeId(this.customOrganizerSeatArchive.getSeatTypeId());
        Assertions.assertEquals(2, organizerSeatArchive.getAvailable_number_of_seats());

        BuyedTicketCollection buyedTicketCollection = this.buyedTicketCollectionRepositoryService.getBySeatTypeIdAndClientId(this.customSeatType.getId(), this.customClient.getId());
        Assertions.assertEquals(0, buyedTicketCollection.getNumberOfTickets());
    }

    private void initializeCustomObjects(){
        this.initializeCustomEventObjects();

        this.customOrganizerSeatArchive = new OrganizerSeatArchive(this.customSeatType, 2, 2);
        this.organizerSeatArchiveRepositoryService.save(this.customOrganizerSeatArchive);

        this.customTicketCollection = new BuyedTicketCollection(customClient, customSeatType, 0);
        this.buyedTicketCollectionRepositoryService.save(this.customTicketCollection);
    }

    private void initializeCustomEventObjects() {
        this.customClient = (Client)userCustomCreator.getUserAndSaveInRepository(Role.CLIENT);

        this.customSeatType = this.seatTypeCustomCreator.getSeatType();

        this.customEvent = this.eventCustomCreator.getLaunchedEvent();
        this.customEvent.setSeatTypes(List.of(this.customSeatType));
        this.eventRepositoryServiceInterface.saveWhenCreating(this.customEvent);
    }
}