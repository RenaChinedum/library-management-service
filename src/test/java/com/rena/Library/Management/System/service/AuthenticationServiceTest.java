package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.TestUtil;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.dtos.request.SignInRequest;
import com.rena.Library.Management.System.dtos.response.SignInResponse;
import com.rena.Library.Management.System.model.Patron;
import com.rena.Library.Management.System.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private PatronRepository patronRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authService;

    @Test
    void createPatron() {

        final PatronDetailRequest request = TestUtil.getRequest();

        when(patronRepositoryMock.save(any(Patron.class))).thenReturn(any(Patron.class));

        final Patron result = authService.createPatron(request);

        assertNotNull(result);

        verify(patronRepositoryMock, times(2)).save(any(Patron.class));
    }

    @Test
    void authenticatePatron() {
        SignInRequest request = new SignInRequest("johndeo@gmail.com", "password");
        PatronDetailRequest request1 = TestUtil.getRequest();
        Patron patron = TestUtil.getPatron(request1);
        String jwt = "mock_jwt_token";

        when(patronRepositoryMock.findByEmail(request.getEmail())).thenReturn(Optional.of(patron));
        when(passwordEncoderMock.matches(request.getPassword(), patron.getPassword())).thenReturn(true);
        when(jwtService.generateToken(patron)).thenReturn(jwt);

        SignInResponse response = authService.authenticatePatron(request);

        verify(jwtService).generateToken(patron);

        assertNotNull(response);
        assertEquals("Log in successful", response.getMessage());
        assertEquals(jwt, response.getToken());
    }

}