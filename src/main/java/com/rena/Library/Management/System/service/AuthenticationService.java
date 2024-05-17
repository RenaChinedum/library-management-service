package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.dtos.request.SignInRequest;
import com.rena.Library.Management.System.dtos.response.SignInResponse;
import com.rena.Library.Management.System.enums.Role;
import com.rena.Library.Management.System.exceptions.ErrorStatus;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Patron;
import com.rena.Library.Management.System.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PatronRepository patronRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Patron createPatron(PatronDetailRequest request) {
        Optional<Patron> patron = patronRepository.findByEmail(request.getEmail());
        if (patron.isPresent()) {
            log.info("===={}", patron);
            throw new LMSystemException(ErrorStatus.USER_ALREADY_EXIST, ErrorStatus.USER_ALREADY_EXIST.getErrorMessage());
        }
        Patron newPatron = getPatron(request);
        newPatron.setRole(Role.PATRON);
        patronRepository.save(newPatron);

        newPatron.setCreatedBy(newPatron.getFirstName() + " " + newPatron.getLastName());
        patronRepository.save(newPatron);

        return newPatron;
    }

    public SignInResponse authenticatePatron(SignInRequest request) {
        Patron patron = patronRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new LMSystemException(ErrorStatus.USER_NOT_FOUND_ERROR, ErrorStatus.USER_NOT_FOUND_ERROR.getErrorMessage()));

        if (!passwordEncoder.matches(request.getPassword(), patron.getPassword()))
            throw new LMSystemException(ErrorStatus.VALIDATION_ERROR, "Incorrect password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwt = jwtService.generateToken(patron);

        SignInResponse response = new SignInResponse();
        response.setMessage("Log in successful");
        response.setToken(jwt);

        return response;
    }


    private Patron getPatron(PatronDetailRequest request) {
        Patron patron = new Patron();
        patron.setFirstName(request.getFirstName());
        patron.setLastName(request.getLastName());
        patron.setEmail(request.getEmail());
        patron.setPassword(passwordEncoder.encode(request.getPassword()));
        patron.setAddress(request.getAddress());
        patron.setMobileNumber(request.getMobileNumber());
        return patron;
    }
}
