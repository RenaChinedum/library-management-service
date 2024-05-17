package com.rena.Library.Management.System.controller;

import com.rena.Library.Management.System.dtos.request.BorrowDetailRequest;
import com.rena.Library.Management.System.dtos.response.UnifiedResponse;
import com.rena.Library.Management.System.model.Borrow;
import com.rena.Library.Management.System.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrow")
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/borrow-book/{bookId}")
    public UnifiedResponse<Borrow> borrowBook(@PathVariable Long bookId, @RequestBody BorrowDetailRequest request) {
        Borrow borrow = borrowService.borrowBook(bookId, request);
        return new UnifiedResponse<>(borrow);
    }

    @PostMapping("/return-book/{borrowId}")
    public UnifiedResponse<Borrow> returnBook(@PathVariable Long borrowId) {
        Borrow borrow = borrowService.returnBook(borrowId);
        return new UnifiedResponse<>(borrow);
    }

    @GetMapping("/all-borrow")
    public UnifiedResponse<Page<Borrow>> getAllBorrow(Pageable pageable) {
        Page<Borrow> borrowPage = borrowService.getAllBorrow(pageable);
        return new UnifiedResponse<>(borrowPage);
    }

    @GetMapping("/all-borrow-by-patron/{patronId}")
    public UnifiedResponse<List<Borrow>> getAllBorrowedByPatron(@PathVariable Long patronId) {
        List<Borrow> borrows = borrowService.getAllBorrowByAPatron(patronId);
        return new UnifiedResponse<>(borrows);
    }

    @GetMapping("/all-unreturned-Borrow")
    public UnifiedResponse<List<Borrow>> getAllUnreturnedBorrow() {
        List<Borrow> unreturnedBorrow = borrowService.getAllUnReturnBorrow();
        return new UnifiedResponse<>(unreturnedBorrow);
    }
}
