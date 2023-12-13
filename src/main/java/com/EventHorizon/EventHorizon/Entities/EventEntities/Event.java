package com.EventHorizon.EventHorizon.Entities.EventEntities;

import com.EventHorizon.EventHorizon.Entities.SeatArchive.SeatType;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Organizer;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false)
    protected String name;
    protected String description;
    protected String eventCategory;
    protected EventType eventType;
    protected Date eventDate;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "location_id",
            referencedColumnName = "id"
    )
    protected Location eventLocation;
    @ManyToOne(
    )
    @JoinColumn(
            name = "ads_id",
            referencedColumnName = "id",
            nullable = false
    )
    protected AdsOption eventAds;
    @ManyToOne(
    )
    @JoinColumn(
            name = "organizer_id",
            referencedColumnName = "id",
            nullable = false
    )
    protected Organizer eventOrganizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<SeatType> seatTypes;
}
