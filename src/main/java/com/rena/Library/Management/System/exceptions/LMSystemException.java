package com.rena.Library.Management.System.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LMSystemException extends RuntimeException{
    private ErrorStatus errorStatus;
    private String message;
    private int errorCode;
    public LMSystemException(ErrorStatus errorStatus, String message){
        this.errorStatus=errorStatus;
        if(message == null){
            this.message=errorStatus.getErrorMessage();
        }else{
            this.message=message;
        }
        this.errorCode = errorStatus.getErrorCode();
    }
}
