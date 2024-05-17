package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.exceptions.ErrorStatus;
import com.rena.Library.Management.System.exceptions.LMSystemException;
import com.rena.Library.Management.System.model.Patron;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatronService extends BaseService {
    private final PatronRepository patronRepository;


    public Patron updatePatronDetail(PatronDetailRequest request) {
        Patron patron = loggedInUser();
        updateFieldIfNotNullOrEmpty(request.getFirstName(), patron::setFirstName);
        updateFieldIfNotNullOrEmpty(request.getLastName(), patron::setLastName);
        updateFieldIfNotNullOrEmpty(request.getEmail(), patron::setEmail);
        updateFieldIfNotNullOrEmpty(request.getAddress(), patron::setAddress);
        updateFieldIfNotNullOrEmpty(request.getMobileNumber(), patron::setMobileNumber);
        return patronRepository.save(patron);
    }

    public String deletePatron(Long id) {
        Patron adminUser = loggedInUser();
        if (isAdmin(adminUser)) {
            Patron patron = patronRepository.findById(id).orElseThrow(()
                    -> new LMSystemException(ErrorStatus.USER_NOT_FOUND_ERROR, "Patron not found"));
            patronRepository.delete(patron);
            return "Client deleted successfully";
        }
        throw new LMSystemException(ErrorStatus.UNAUTHORIZED_ERROR, "You're not authorised to delete a patron");
    }

    @Cacheable(value = "patronCache")
    public Page<Patron> getAllPatron(Pageable pageable) {
        return patronRepository.findAll(pageable);
    }

    @Cacheable(value = "patronCache", key = "#id")
    public Patron getAPatron(Long id) {
        return patronRepository.findById(id).orElseThrow(()
                -> new LMSystemException(ErrorStatus.USER_NOT_FOUND_ERROR, "Patron not found"));
    }


}
