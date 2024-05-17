package com.rena.Library.Management.System.controller;

import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.dtos.request.SignInRequest;
import com.rena.Library.Management.System.dtos.response.SignInResponse;
import com.rena.Library.Management.System.dtos.response.UnifiedResponse;
import com.rena.Library.Management.System.model.Patron;
import com.rena.Library.Management.System.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public UnifiedResponse<Patron> createClient(@RequestBody PatronDetailRequest request) {
        Patron patron = authenticationService.createPatron(request);
        return new UnifiedResponse<>(patron);
    }

    @PostMapping("/authenticate")
    public UnifiedResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authenticationService.authenticatePatron(request);
        return new UnifiedResponse<>(response);
    }
}
