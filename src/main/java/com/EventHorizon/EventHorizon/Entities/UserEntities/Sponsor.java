package com.EventHorizon.EventHorizon.Entities.UserEntities;

import com.EventHorizon.EventHorizon.Entities.SeatArchive.SponsorSeatArchive;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "sponsor_tbl")
public class Sponsor  extends User{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id", referencedColumnName = "id")
    private Information information;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SponsorSeatArchive> sponsorSeatArchiveList;

    @Override
    public int getInformationId() {
        return information.getId();
    }
}
