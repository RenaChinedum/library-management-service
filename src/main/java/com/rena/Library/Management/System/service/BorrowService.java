package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.BookRepository;
import com.rena.Library.Management.System.Repository.BorrowRepository;
import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.dtos.request.BorrowDetailRequest;
import com.rena.Library.Management.System.exceptions.ErrorStatus;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Book;
import com.rena.Library.Management.System.model.Borrow;
import com.rena.Library.Management.System.model.Patron;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorrowService extends BaseService {
    private final BorrowRepository borrowRepository;
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;

    private static final BigDecimal PENAL_CHARGE = BigDecimal.valueOf(25);

    @Transactional
    public Borrow borrowBook(Long bookId, BorrowDetailRequest request) {
        Patron patron = loggedInUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Book not found"));
        if (book.isBorrowed()) {
            throw new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "This book is not available for now, check back later");
        }
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setPatron(patron);
        borrow.setDateBorrowed(LocalDate.now());
        borrow.setDueDate(request.getDueDate());
        book.setBorrowed(true);
        bookRepository.save(book);
        return borrowRepository.save(borrow);
    }

    @Transactional
    public Borrow returnBook(Long borrowId) {
        Borrow borrow = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.RESOURCE_NOT_FOUND_ERROR, "Not found"));
        Book book = borrow.getBook();
        book.setBorrowed(false);
        borrow.setDateReturned(LocalDate.now());
        if (isDueDatePast(borrow)) {
            borrow.setPenalCharge(PENAL_CHARGE.multiply(BigDecimal.valueOf(getDaysPastDueDate(borrow))));
            log.info("========>penal charge<======={}", PENAL_CHARGE.multiply(BigDecimal.valueOf(getDaysPastDueDate(borrow))));
        }
        borrow.setReturned(true);
        bookRepository.save(book);
        return borrowRepository.save(borrow);
    }

    @Cacheable(value = "borrowCache", key = "#patronId")
    public List<Borrow> getAllBorrowByAPatron(Long patronId) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new LMSystemException(ErrorStatus.USER_NOT_FOUND_ERROR, "Patron not found"));
        return borrowRepository.findBorrowByPatron(patron);
    }

    @Cacheable(value = "borrowCache")
    public Page<Borrow> getAllBorrow(Pageable pageable) {
        return borrowRepository.findAll(pageable);
    }

    @Cacheable(value = "borrowCache")
    public List<Borrow> getAllUnReturnBorrow() {
        return borrowRepository.findAllBorrowedBooks();
    }

    private boolean isDueDatePast(Borrow borrow) {
        LocalDate now = LocalDate.now();
        LocalDate dueDate = borrow.getDueDate();
        return dueDate.isBefore(now);
    }

    private long getDaysPastDueDate(Borrow borrow) {
        LocalDate now = LocalDate.now();
        LocalDate dueDate = borrow.getDueDate();
        if (dueDate.isBefore(now)) {
            long daysPassed = ChronoUnit.DAYS.between(dueDate, now);
            return daysPassed;
        } else {
            return 0;
        }
    }
}
