package com.rena.Library.Management.System.service;

import com.rena.Library.Management.System.enums.Role;
import com.rena.Library.Management.System.model.Patron;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BaseService {


    public Patron loggedInUser() {
        return (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isAdmin(Patron patron) {
        return patron.getRole().equals(Role.ADMIN);
    }

    public void updateFieldIfNotNullOrEmpty(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }
}
