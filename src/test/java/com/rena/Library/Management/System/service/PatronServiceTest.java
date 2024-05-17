package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.TestUtil;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.enums.Role;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronServiceTest {
    @Mock
    private  PatronRepository patronRepositoryMock;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void updatePatronDetail() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron expected = TestUtil.getPatron(request);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(expected);
        SecurityContextHolder.setContext(securityContext);

        when(patronRepositoryMock.save(any(Patron.class))).thenReturn(expected);

        Patron result = patronService.updatePatronDetail(request);

        assertNotNull(result);
        assertEquals(expected, result);

        verify(patronRepositoryMock).save(any(Patron.class));
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

    @Test
    void deletePatron() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron expected = TestUtil.getPatron(request);
        expected.setRole(Role.ADMIN);
        expected.setId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(expected);
        SecurityContextHolder.setContext(securityContext);
        when(patronRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        String result = patronService.deletePatron(expected.getId());

        assertNotNull(result);
        verify(patronRepositoryMock).findById(any(Long.class));
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();

    }

    @Test
    void getAllPatron() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron expected = TestUtil.getPatron(request);
        expected.setId(1L);
        Page<Patron> expectedPatronPage = mock(Page.class);

        when(patronRepositoryMock.findAll(pageable)).thenReturn(expectedPatronPage);

        Page<Patron> result = patronService.getAllPatron(pageable);

        assertNotNull(result);

        verify(patronRepositoryMock).findAll(pageable);
    }

    @Test
    void getAPatron() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron expected = TestUtil.getPatron(request);
        expected.setId(1L);

        when(patronRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Patron result = patronService.getAPatron(expected.getId());

        assertNotNull(result);
        assertEquals(expected, result);

        verify(patronRepositoryMock).findById(any(Long.class));
    }
    @Test
    void getAPatron_unsuccessful() {
        when(patronRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows( LMSystemException.class, ()-> patronService.getAPatron(1L));

    }

}