package com.iread.form;

import com.iread.model.IReadBook;

import java.math.BigDecimal;

public class BookForm {
    private IReadBook book;
    private BigDecimal avgRating;

    public IReadBook getBook() {
        return book;
    }

    public void setBook(IReadBook book) {
        this.book = book;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }
}
