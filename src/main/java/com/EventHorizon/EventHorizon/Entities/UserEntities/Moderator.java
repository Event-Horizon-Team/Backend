package com.EventHorizon.EventHorizon.Entities.UserEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "moderator_tbl")
public class Moderator extends User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "information_id", referencedColumnName = "id")
    private Information information;

    @Override
    public int getInformationId() {
        return information.getId();
    }
}
