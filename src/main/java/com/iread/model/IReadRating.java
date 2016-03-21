package com.iread.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rating")
public class IReadRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "bigint", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", columnDefinition = "bigint", referencedColumnName = "id")
    private IReadBook book;

    @Column(name = "rate", columnDefinition = "numeric")
    private BigDecimal rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IReadBook getBook() {
        return book;
    }

    public void setBook(IReadBook book) {
        this.book = book;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
