package com.iread.form;

public class UserAddBookForm {
    private String bookName;
    private String bookAuthor;
    private Integer bookYear;
    private String bookAnnotation;

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
}
