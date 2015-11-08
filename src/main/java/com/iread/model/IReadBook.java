package com.iread.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class IReadBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "BOOK_AUTHOR")
    private String bookAuthor;

    @Column(name = "PUBLICATION_YEAR", columnDefinition = "int")
    private Integer publicationYear;

    @Column(name = "ANNOTATION", columnDefinition = "text")
    private String annotation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getPublicationDate() {
        return publicationYear;
    }

    public void setPublicationDate(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
