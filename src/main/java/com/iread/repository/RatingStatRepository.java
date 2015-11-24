package com.iread.repository;

import com.iread.model.IReadBook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

public class RatingStatRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public BigDecimal avgBookRating(IReadBook book) {
        Query query = entityManager.createNativeQuery("select avg(rate) from rating where BOOK_id = :book_id");
        return (BigDecimal) query.setParameter("book_id", book.getId()).getSingleResult();
    }
}
