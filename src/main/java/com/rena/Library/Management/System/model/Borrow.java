package com.rena.Library.Management.System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rena.Library.Management.System.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrow")
public class Borrow extends AuditEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate dateBorrowed;
    private LocalDate dueDate;
    private boolean returned;
    private LocalDate dateReturned;
    private BigDecimal penalCharge;

}
