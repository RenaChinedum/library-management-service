package com.rena.Library.Management.System;

import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.model.Patron;

public class TestUtil {
    public static Patron getPatron(PatronDetailRequest request){
        Patron patron = new Patron();
        patron.setFirstName(request.getFirstName());
        patron.setLastName(request.getLastName());
        patron.setEmail(request.getEmail());
        patron.setPassword(request.getPassword());
        return patron;
    }
    public static PatronDetailRequest getRequest(){
        PatronDetailRequest request = new PatronDetailRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("johndoe@gmail.com");
        request.setPassword("John@doe1");
        return request;
    }
}
