package com.rena.Library.Management.System.dtos.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
