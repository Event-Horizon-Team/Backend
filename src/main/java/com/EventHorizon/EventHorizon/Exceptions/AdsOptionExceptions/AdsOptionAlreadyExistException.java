package com.EventHorizon.EventHorizon.Exceptions.AdsOptionExceptions;

import com.EventHorizon.EventHorizon.Exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class AdsOptionAlreadyExistException extends BaseException {
    public AdsOptionAlreadyExistException() {
        this.message=" Ads Already Found";
        this.httpStatus= HttpStatus.CONFLICT;
    }
}
