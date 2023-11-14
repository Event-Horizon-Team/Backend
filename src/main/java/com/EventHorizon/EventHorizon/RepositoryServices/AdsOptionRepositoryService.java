package com.EventHorizon.EventHorizon.RepositoryServices;

import com.EventHorizon.EventHorizon.DTOs.AdsOptionDTO;
import com.EventHorizon.EventHorizon.Entities.AdsOption;
import com.EventHorizon.EventHorizon.Exceptions.AdsAlreadyFoundException;
import com.EventHorizon.EventHorizon.Exceptions.AdsNotFoundException;
import com.EventHorizon.EventHorizon.Exceptions.AdsOptionNotFoundException;
import com.EventHorizon.EventHorizon.Repository.AdsOptionRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdsOptionRepositoryService {
    @Autowired
    private AdsOptionRepositry adsOptionRepositry;

    public AdsOption saveAdsOptionWhenCreating(AdsOption adsOption) {
        if (adsOption.getId() != 0)
            throw new AdsAlreadyFoundException();

        adsOptionRepositry.save(adsOption);
        return adsOption;
    }

    public AdsOption updateAdsOption(int id, AdsOption adsOption) {
        Optional<AdsOption> optionalOldAds = adsOptionRepositry.findById(id);

        if (adsOption.getId() != 0)
            throw new AdsAlreadyFoundException();

        if (!optionalOldAds.isPresent())
            throw new AdsNotFoundException();


        adsOption.setId(id);
        adsOptionRepositry.save(adsOption);
        return adsOption;
    }

    public AdsOption findAdsOptionById(int id) {
        Optional<AdsOption> adsOption = adsOptionRepositry.findById(id);


        if (!adsOption.isPresent())
            throw new AdsNotFoundException();

        return adsOption.get();
    }

    public AdsOption getAdsOptionFromDTO(AdsOptionDTO adsOptionDTO)
    {
        Optional<AdsOption> optionalAdsOption = this.adsOptionRepositry.findById(adsOptionDTO.id);
        if (!optionalAdsOption.isPresent())
            throw new AdsOptionNotFoundException();
        return optionalAdsOption.get();
    }

    public AdsOptionDTO getDTOFromAdsOption(AdsOption adsOption)
    {
        AdsOptionDTO adsOptionDTO = new AdsOptionDTO();
        adsOptionDTO.id = adsOption.getId();
        adsOptionDTO.name = adsOption.getName();
        return adsOptionDTO;
    }
}
