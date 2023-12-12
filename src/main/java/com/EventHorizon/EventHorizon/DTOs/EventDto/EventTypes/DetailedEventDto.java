package com.EventHorizon.EventHorizon.DTOs.EventDto.EventTypes;

import com.EventHorizon.EventHorizon.DTOs.EventDto.AdsOptionDto;
import com.EventHorizon.EventHorizon.Entities.enums.EventType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class DetailedEventDto extends EventDto {

    protected AdsOptionDto eventAds;
    protected EventType eventType;
}
