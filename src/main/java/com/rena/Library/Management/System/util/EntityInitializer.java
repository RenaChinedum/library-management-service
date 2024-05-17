package com.rena.Library.Management.System.util;

import com.rena.Library.Management.System.Repository.PatronRepository;
import com.rena.Library.Management.System.enums.Role;
import com.rena.Library.Management.System.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class EntityInitializer implements ApplicationRunner {

    private final PatronRepository patronRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EntityInitializer(PatronRepository patronRepository, PasswordEncoder passwordEncoder) {
        this.patronRepository = patronRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       Patron admin = new Patron();
       admin.setFirstName("Admin 1");
       admin.setLastName("Admin");
       admin.setEmail("admin1@gmail.com");
       admin.setRole(Role.ADMIN);
       admin.setPassword(passwordEncoder.encode("Admin1@maid"));
//       patronRepository.save(admin);
    }
}
