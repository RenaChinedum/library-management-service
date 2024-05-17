package com.rena.Library.Management.System.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDetailRequest {
    private LocalDate dueDate;
}
