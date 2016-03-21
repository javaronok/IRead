package com.iread.service;

import com.iread.model.IReadRating;
import org.lenskit.data.ratings.Rating;

import java.util.ArrayList;
import java.util.List;


public class RatingRecommendationService {
    protected List<Rating> compileRatings(List<IReadRating> ratings) {
        List<Rating> rs = new ArrayList<Rating>();

        for (IReadRating r : ratings) {
            rs.add(Rating.create(r.getUser().getId(), r.getBook().getId(), r.getRate().doubleValue()));
        }

        return rs;
    }
}
