package com.rena.Library.Management.System.Repository;

import com.rena.Library.Management.System.model.Borrow;
import com.rena.Library.Management.System.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findBorrowByPatron(Patron patron);

    @Query(value = "SELECT * FROM borrow WHERE returned = false", nativeQuery = true)
    List<Borrow> findAllBorrowedBooks();
}
