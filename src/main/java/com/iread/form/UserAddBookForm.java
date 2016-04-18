package com.iread.form;

import com.iread.model.IReadTag;

import java.util.Set;

public class UserAddBookForm {
    private String bookName;
    private String bookAuthor;
    private Integer bookYear;
    private String bookAnnotation;
    private Set<IReadTag> tags;

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
}
