package com.rena.Library.Management.System.Repository;

import com.rena.Library.Management.System.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
