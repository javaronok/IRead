package com.iread.form;

import com.iread.model.IReadBook;

import java.math.BigDecimal;
import java.util.Objects;

public class RecommendationResultForm {
    private IReadBook book;
    private BigDecimal avgRating;
    private BigDecimal score;

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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecommendationResultForm)) return false;
        RecommendationResultForm that = (RecommendationResultForm) o;
        return Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book);
    }
}
