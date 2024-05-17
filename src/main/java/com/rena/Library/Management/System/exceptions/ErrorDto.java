package com.rena.Library.Management.System.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private int errorCode;
    private String message;

    public ErrorDto(LMSystemException tamayozException){
        this.errorCode = tamayozException.getErrorCode();
        this.message = tamayozException.getMessage();
    }

}
