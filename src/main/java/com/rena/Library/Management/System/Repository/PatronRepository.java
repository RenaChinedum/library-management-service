package com.rena.Library.Management.System.Repository;
import com.rena.Library.Management.System.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findByEmail(String email);

}
