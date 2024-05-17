package com.rena.Library.Management.System.Repository;

import com.rena.Library.Management.System.model.Borrow;
import com.rena.Library.Management.System.model.Patron;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowRepositoryTest {
    @Mock
    private BorrowRepository borrowRepository;

    @Test
    void testFindBorrowByPatron() {
        Patron patron = new Patron();
        List<Borrow> borrows = List.of(new Borrow(), new Borrow());

        when(borrowRepository.findBorrowByPatron(patron)).thenReturn(borrows);

        List<Borrow> result = borrowRepository.findBorrowByPatron(patron);

        verify(borrowRepository).findBorrowByPatron(patron);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllBorrowedBooks() {
        List<Borrow> borrowedBooks = List.of(new Borrow(), new Borrow());


        when(borrowRepository.findAllBorrowedBooks()).thenReturn(borrowedBooks);

        List<Borrow> result = borrowRepository.findAllBorrowedBooks();
        verify(borrowRepository).findAllBorrowedBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}