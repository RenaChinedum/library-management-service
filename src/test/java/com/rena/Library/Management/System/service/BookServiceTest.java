package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.BookRepository;
import com.rena.Library.Management.System.TestUtil;
import com.rena.Library.Management.System.dtos.request.BookDetailRequest;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.enums.Role;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Book;
import com.rena.Library.Management.System.model.Patron;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private BookService bookService;

    @Test
    void addNewBook() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron patron = TestUtil.getPatron(request);
        patron.setRole(Role.ADMIN);
        patron.setId(1L);

        BookDetailRequest request1 = setRequest();
        Book expected = setBook(request1);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(patron);
        SecurityContextHolder.setContext(securityContext);
        when(bookRepositoryMock.save(any(Book.class))).thenReturn(expected);

        Book result = bookService.addNewBook(request1);

        assertNotNull(result);

        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
        verify(bookRepositoryMock).save(any(Book.class));
    }

    @Test
    void updateBookDetails() {
        PatronDetailRequest request = TestUtil.getRequest();
        Patron patron = TestUtil.getPatron(request);
        patron.setRole(Role.ADMIN);
        patron.setId(1L);

        BookDetailRequest request1 = setRequest();
        Book expected = setBook(request1);
        expected.setId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(patron);
        SecurityContextHolder.setContext(securityContext);
        when(bookRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(bookRepositoryMock.save(any(Book.class))).thenReturn(expected);

        Book result = bookService.updateBookDetails(expected.getId(), request1);

        assertNotNull(result);

        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
        verify(bookRepositoryMock).save(any(Book.class));
    }

    @Test
    void getABook() {
       BookDetailRequest request = setRequest();
       Book expected = setBook(request);
        expected.setId(1L);

        when(bookRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Book result = bookService.getABook(expected.getId());

        assertNotNull(result);
        assertEquals(expected, result);

        verify(bookRepositoryMock).findById(any(Long.class));
    }
  @Test
    void getABook_unsuccessful() {
      when(bookRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

      assertThrows( LMSystemException.class, ()-> bookService.getABook(1L));

  }

    @Test
    void getAllBooks() {
        Page<Book> expectedBookPage = mock(Page.class);

        when(bookRepositoryMock.findAll(pageable)).thenReturn(expectedBookPage);

        Page<Book> result = bookService.getAllBooks(pageable);

        assertNotNull(result);

        verify(bookRepositoryMock).findAll(pageable);
    }

    @Test
    void deleteABook() {
        BookDetailRequest request = setRequest();
        Book expected = setBook(request);
        expected.setId(1L);

        when(bookRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        String result = bookService.deleteABook(expected.getId());

        assertNotNull(result);
        verify(bookRepositoryMock).findById(any(Long.class));
    }
    private BookDetailRequest setRequest(){
        return BookDetailRequest.builder()
                .title("A book for thought")
                .author("Writ Arthur")
                .isbn("234-43-2-67-855")
                .build();
    }
    private Book setBook(BookDetailRequest request){
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        return book;
    }
}