package com.iread.form;

import com.iread.model.IReadTag;

import java.util.Set;

public class UserAddBookForm {
    private String bookName;
    private String authorLastName;
    private String authorFirstName;
    private String authorPatronymic;
    private Integer bookYear;
    private String bookAnnotation;
    private String coverFileUid;
    private Set<IReadTag> tags;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorPatronymic() {
        return authorPatronymic;
    }

    public void setAuthorPatronymic(String authorPatronymic) {
        this.authorPatronymic = authorPatronymic;
    }

    public Integer getBookYear() {
        return bookYear;
    }

    public void setBookYear(Integer bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookAnnotation() {
        return bookAnnotation;
    }

    public void setBookAnnotation(String bookAnnotation) {
        this.bookAnnotation = bookAnnotation;
    }

    public Set<IReadTag> getTags() {
        return tags;
    }

    public void setTags(Set<IReadTag> tags) {
        this.tags = tags;
    }

    public String getCoverFileUid() {
        return coverFileUid;
    }

    public void setCoverFileUid(String coverFileUid) {
        this.coverFileUid = coverFileUid;
    }
}
