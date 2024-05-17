package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.BookRepository;
import com.rena.Library.Management.System.Repository.BorrowRepository;
import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.TestUtil;
import com.rena.Library.Management.System.dtos.request.BorrowDetailRequest;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.model.Book;
import com.rena.Library.Management.System.model.Borrow;
import com.rena.Library.Management.System.model.Patron;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;
    @Mock
    private PatronRepository patronRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;
    @InjectMocks
    private BorrowService borrowService;

    @Test
    void borrowBook() {
        Long bookId = 1L;
        BorrowDetailRequest request = new BorrowDetailRequest(LocalDate.now().plusDays(7));
        PatronDetailRequest request1 = TestUtil.getRequest();
        Patron patron = TestUtil.getPatron(request1);
        Book book = new Book();
        book.setId(1L);
        Borrow borrow = new Borrow();
        borrow.setPatron(patron);
        borrow.setBook(book);



        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(patron);
        SecurityContextHolder.setContext(securityContext);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowRepository.save(any(Borrow.class))).thenReturn(new Borrow());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Borrow result = borrowService.borrowBook(bookId, request);

        verify(bookRepository).findById(bookId);
        verify(borrowRepository).save(any(Borrow.class));
        verify(bookRepository).save(any(Book.class));

        assertNotNull(result);
        assertEquals(patron, borrow.getPatron());
        assertEquals(book, borrow.getBook());
    }

    @Test
    void returnBook() {
        Long bookId = 1L;
        BorrowDetailRequest request = new BorrowDetailRequest(LocalDate.now().plusDays(7));
        Book book = new Book();
        book.setId(1L);
        Borrow borrow = new Borrow();
        borrow.setId(1L);
        borrow.setBook(book);
        borrow.setDueDate(LocalDate.now().plusDays(7));

        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        Borrow returnedBorrow = borrowService.returnBook(1L);

        verify(borrowRepository).findById(any(Long.class));
        verify(bookRepository).save(any(Book.class));
        verify(borrowRepository).save(any(Borrow.class));

        assertNotNull(returnedBorrow);
        assertFalse(book.isBorrowed());
    }
    @Test
    void testReturnBook_Late() {
        Long borrowId = 1L;
        Borrow borrow = new Borrow();
        Book book = new Book();
        book.setId(1L);
        borrow.setBook(book);
        borrow.setDueDate(LocalDate.now().minusDays(7));

        when(borrowRepository.findById(any(Long.class))).thenReturn(Optional.of(borrow));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);

        Borrow returnedBorrow = borrowService.returnBook(1L);

        verify(borrowRepository).findById(any(Long.class));
        verify(bookRepository).save(any(Book.class));
        verify(borrowRepository).save(any(Borrow.class));

        assertNotNull(returnedBorrow);
        assertFalse(book.isBorrowed());
        assertEquals(LocalDate.now(), returnedBorrow.getDateReturned());
        assertTrue(returnedBorrow.isReturned());
        assertTrue(returnedBorrow.getPenalCharge().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void getAllBorrowByAPatron() {
        Long patronId = 1L;
        PatronDetailRequest request = TestUtil.getRequest();
        Patron patron = TestUtil.getPatron(request);
        List<Borrow> expectedBorrows = List.of(new Borrow(), new Borrow());

        when(patronRepository.findById(any(Long.class))).thenReturn(Optional.of(patron));
        when(borrowRepository.findBorrowByPatron(patron)).thenReturn(expectedBorrows);

        List<Borrow> result = borrowService.getAllBorrowByAPatron(patronId);

        verify(patronRepository, times(1)).findById(any(Long.class));
        verify(borrowRepository).findBorrowByPatron(any(Patron.class));

       assertNotNull(result);
    }

    @Test
    void getAllBorrow() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Borrow> expectedBorrows = new PageImpl<>(List.of(new Borrow(), new Borrow()));

        when(borrowRepository.findAll(pageable)).thenReturn(expectedBorrows);

        Page<Borrow> result= borrowService.getAllBorrow(pageable);

        verify(borrowRepository, times(1)).findAll(pageable);

        assertNotNull(result);
        assertEquals(expectedBorrows, result);

    }

    @Test
    void getAllUnReturnBorrow() {
        List<Borrow> expectedBorrows = List.of(new Borrow(), new Borrow());

        when(borrowRepository.findAllBorrowedBooks()).thenReturn(expectedBorrows);

        List<Borrow> result = borrowService.getAllUnReturnBorrow();


        verify(borrowRepository, times(1)).findAllBorrowedBooks();

        assertNotNull(result);
        assertEquals(expectedBorrows, result);
    }
}