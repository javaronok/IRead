package com.iread.model;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class IReadBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "BOOK_AUTHOR")
    private String bookAuthor;

    @Column(name = "PUBLICATION_YEAR", columnDefinition = "smallint")
    private Integer publicationYear;

    @Column(name = "ANNOTATION", columnDefinition = "blob")
    private String annotation;

    @ManyToMany
    @JoinTable(name = "book_tags",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "ID")})
    Set<IReadTag> tags = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Set<IReadTag> getTags() {
        return tags;
    }

    public void setTags(Set<IReadTag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IReadBook)) return false;
        IReadBook book = (IReadBook) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
