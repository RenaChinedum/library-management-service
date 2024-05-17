package com.rena.Library.Management.System.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnifiedResponse<T> {
    private String message;
    private boolean hasError;
    private int responseCode;
    private T data;

    public UnifiedResponse(T data){
        this.message = "success";
        this.responseCode = 200;
        this.data = data;
    }
}
