package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.BookRepository;
import com.rena.Library.Management.System.dtos.request.BookDetailRequest;
import com.rena.Library.Management.System.exceptions.ErrorStatus;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Book;
import com.rena.Library.Management.System.model.Patron;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService extends BaseService {
    private final BookRepository bookRepository;

    public Book addNewBook(BookDetailRequest request) {
        Patron admin = loggedInUser();
        if (isAdmin(admin)) {
            Book book = setBookDetails(request);
            return bookRepository.save(book);
        }
        throw new LMSystemException(ErrorStatus.UNAUTHORIZED_ERROR, "Only admin can add a book");
    }

    public Book updateBookDetails(Long bookId, BookDetailRequest request) {
        Patron admin = loggedInUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Book not found"));
        if (isAdmin(admin)) {
            updateFieldIfNotNullOrEmpty(request.getTitle(), book::setTitle);
            updateFieldIfNotNullOrEmpty(request.getAuthor(), book::setAuthor);
            updateFieldIfNotNullOrEmpty(request.getIsbn(), book::setIsbn);
            updateFieldIfNotNullOrEmpty(request.getPublicationYear(), book::setPublicationYear);
            updateFieldIfNotNullOrEmpty(request.getChapters(), book::setChapters);
            updateFieldIfNotNullOrEmpty(request.getPages(), book::setPages);
            bookRepository.save(book);
        }
        return book;
    }

    @Cacheable(value = "bookCache", key = "#bookId")
    public Book getABook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Book not found"));
    }

    @Cacheable(value = "bookCache")
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    public String deleteABook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Book not found"));
        bookRepository.delete(book);
        return "Book deleted successfully";
    }

    private Book setBookDetails(BookDetailRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublicationYear(request.getPublicationYear());
        book.setChapters(request.getChapters());
        book.setPages(request.getPages());
        return book;
    }
}
