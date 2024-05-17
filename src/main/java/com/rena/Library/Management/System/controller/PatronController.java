package com.rena.Library.Management.System.controller;

import com.rena.Library.Management.System.dtos.request.PatronDetailRequest;
import com.rena.Library.Management.System.dtos.response.UnifiedResponse;
import com.rena.Library.Management.System.model.Patron;
import com.rena.Library.Management.System.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/patron")
@RequiredArgsConstructor
@RestController
public class PatronController {
    private final PatronService patronService;

    @PutMapping("/update")
    public UnifiedResponse<Patron> updatePatron(@RequestBody PatronDetailRequest request) {
        return new UnifiedResponse<>(patronService.updatePatronDetail(request));
    }


    @DeleteMapping("/delete/{id}")
    public UnifiedResponse<String> deletePatron(@PathVariable Long id) {
        return new UnifiedResponse<>(patronService.deletePatron(id));
    }

    @GetMapping("/{id}")
    public UnifiedResponse<Patron> getAPatron(@PathVariable Long id) {
        return new UnifiedResponse<>(patronService.getAPatron(id));
    }

    @GetMapping("/all-patrons")
    public UnifiedResponse<Page<Patron>> allPatrons(Pageable pageable) {
        return new UnifiedResponse<>(patronService.getAllPatron(pageable));
    }
}
