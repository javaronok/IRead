package com.iread.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "books")
public class IReadBook {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "PKIReadBook", sequenceName = "SEQ_BOOKS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PKIReadBook")
    private Long id;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "AUTHOR_FIRSTNAME")
    private String authorFirstName;

    @Column(name = "AUTHOR_LASTNAME")
    private String authorLastName;

    @Column(name = "AUTHOR_PATRONYMIC")
    private String authorPatronymic;

    @Column(name = "PUBLICATION_YEAR", columnDefinition = "smallint")
    private Integer publicationYear;

    @Column(name = "ANNOTATION", columnDefinition = "blob")
    private String annotation;

    @Column(name = "COVER_CAPTURE")
    private String cover;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorPatronymic() {
        return authorPatronymic;
    }

    public void setAuthorPatronymic(String authorPatronymic) {
        this.authorPatronymic = authorPatronymic;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthorName() {
        StringBuilder sb = new StringBuilder();

        if (getAuthorLastName() != null)
          sb.append(getAuthorLastName());

        if (getAuthorFirstName() != null) {
            if (sb.length() > 0)
                sb.append(" ");

            sb.append(getAuthorFirstName());
        }

        if (getAuthorPatronymic() != null) {
            if (sb.length() > 0)
                sb.append(" ");

            sb.append(getAuthorPatronymic());
        }

        return sb.toString();
    }

    public String getTagList() {
        StringJoiner joiner = new StringJoiner(",");

        for (IReadTag tag : tags) {
            joiner.add(tag.getTagName());
        }

        return joiner.toString();
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
