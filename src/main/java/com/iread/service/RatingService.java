package com.iread.service;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.repository.IReadRatingRepository;
import com.iread.repository.RatingStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private IReadRatingRepository iReadRatingRepository;

    @Autowired
    private RatingStatRepository ratingRepository;

    public List<IReadRating> getAllRatings() {
        return iReadRatingRepository.findAll();
    }

    public IReadRating lookupRating(User user, IReadBook book) {
        List<IReadRating> ratings = iReadRatingRepository.findByUserAndBook(user, book);
        return !ratings.isEmpty() ? ratings.get(0) : null;
    }

    @Transactional(readOnly = false)
    public void postBookRating(User user, IReadBook book, BigDecimal rate) {
        IReadRating rating = lookupRating(user, book);
        if (rating == null) {  // Не нашли
            rating = new IReadRating();
            rating.setUser(user);
            rating.setBook(book);
        }
        rating.setRate(rate);

        iReadRatingRepository.save(rating);
    }

    public BigDecimal avgBookRating(IReadBook book) {
        return ratingRepository.avgBookRating(book);
    }

}
