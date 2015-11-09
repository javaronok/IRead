package com.iread.service;

import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.repository.IReadRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private IReadRatingRepository IReadRatingRepository;

    public List<IReadRating> getAllRatings() {
        return IReadRatingRepository.findAll();
    }
}
