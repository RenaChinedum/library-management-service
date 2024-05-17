package com.rena.Library.Management.System.model;

import com.rena.Library.Management.System.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publicationYear;
    private String isbn;
    private String pages;
    private String chapters;
    private boolean borrowed;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrow> borrows;
}
