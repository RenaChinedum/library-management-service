package com.rena.Library.Management.System.Repository;

import com.rena.Library.Management.System.model.Book;
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
class BookRepositoryTest {
    @Mock
    private BookRepository bookRepository;

    @Test
    void testFindAll() {

        List<Book> books = List.of(new Book(), new Book());

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookRepository.findAll();

        verify(bookRepository).findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {

        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        Optional<Book> result = bookRepository.findById(1L);

        verify(bookRepository).findById(1L);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void testFindAllPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(new Book(), new Book()));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<Book> result = bookRepository.findAll(pageable);

        verify(bookRepository).findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }
}