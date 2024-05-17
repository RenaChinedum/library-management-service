package com.rena.Library.Management.System.controller;

import com.rena.Library.Management.System.dtos.request.BookDetailRequest;
import com.rena.Library.Management.System.dtos.response.UnifiedResponse;
import com.rena.Library.Management.System.model.Book;
import com.rena.Library.Management.System.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add-book")
    public UnifiedResponse<Book> addNewBook(@RequestBody BookDetailRequest request) {
        Book book = bookService.addNewBook(request);
        return new UnifiedResponse<>(book);
    }

    @PostMapping("/update-book/{bookId}")
    public UnifiedResponse<Book> updateBookDetails(@PathVariable Long bookId, @RequestBody BookDetailRequest request) {
        Book book = bookService.updateBookDetails(bookId, request);
        return new UnifiedResponse<>(book);
    }

    @GetMapping("/{bookId}")
    public UnifiedResponse<Book> getABook(@PathVariable Long bookId) {
        Book book = bookService.getABook(bookId);
        return new UnifiedResponse<>(book);
    }

    @GetMapping("/all-books")
    public UnifiedResponse<Page<Book>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        return new UnifiedResponse<>(books);
    }

    @DeleteMapping("/{bookId}")
    public UnifiedResponse<String> deleteBook(@PathVariable Long bookId) {
        String deleteBookResponse = bookService.deleteABook(bookId);
        return new UnifiedResponse<>(deleteBookResponse);
    }
}
