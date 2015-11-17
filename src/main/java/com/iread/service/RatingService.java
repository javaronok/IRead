package com.iread.service;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.repository.IReadRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private IReadRatingRepository iReadRatingRepository;

    public List<IReadRating> getAllRatings() {
        return iReadRatingRepository.findAll();
    }

    public IReadRating lookupRating(User user, IReadBook book) {
        List<IReadRating> ratings = iReadRatingRepository.findByUserAndBook(user, book);
        return !ratings.isEmpty() ? ratings.get(0) : null;
    }

    @Transactional(readOnly = false)
    public void postBookRating(User user, IReadBook book, Integer rate) {
        IReadRating rating = lookupRating(user, book);
        if (rating == null) {  // Не нашли
            rating = new IReadRating();
            rating.setUser(user);
            rating.setBook(book);
        }
        rating.setRate(rate);

        iReadRatingRepository.save(rating);
    }
}
