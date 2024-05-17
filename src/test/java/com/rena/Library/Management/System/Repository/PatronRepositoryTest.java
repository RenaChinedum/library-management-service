package com.rena.Library.Management.System.Repository;

import com.rena.Library.Management.System.model.Patron;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatronRepositoryTest {

    @Mock
    private PatronRepository patronRepository;

    @Test
    void testFindAll() {
        List<Patron> patrons = List.of(new Patron(), new Patron());

        when(patronRepository.findAll()).thenReturn(patrons);

        List<Patron> result = patronRepository.findAll();

        verify(patronRepository).findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    void testFindById() {

        Patron patron = new Patron();
        patron.setId(1L);

        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));

        Optional<Patron> result = patronRepository.findById(1L);

        verify(patronRepository).findById(1L);

        assertTrue(result.isPresent());
        assertEquals(patron, result.get());
    }

    @Test
    void testFindByEmail() {
        String email = "janedoe@example.com";
        Patron patron = new Patron();
        patron.setEmail(email);

        when(patronRepository.findByEmail(email)).thenReturn(Optional.of(patron));

        Optional<Patron> result = patronRepository.findByEmail(email);


        verify(patronRepository).findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(patron, result.get());
    }

    @Test
    void testFindAllPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Patron> patronPage = new PageImpl<>(List.of(new Patron(), new Patron()));

        when(patronRepository.findAll(pageable)).thenReturn(patronPage);

        Page<Patron> result = patronRepository.findAll(pageable);

        verify(patronRepository).findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }
}