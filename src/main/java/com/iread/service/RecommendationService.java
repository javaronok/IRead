package com.iread.service;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.repository.IReadBookRepository;
import com.iread.repository.IReadRatingRepository;
import org.grouplens.lenskit.vectors.similarity.PearsonCorrelation;
import org.grouplens.lenskit.vectors.similarity.VectorSimilarity;
import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.LenskitRecommenderEngine;
import org.lenskit.api.ItemRecommender;
import org.lenskit.api.ItemScorer;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.ratings.Rating;
import org.lenskit.knn.user.LiveNeighborFinder;
import org.lenskit.knn.user.NeighborFinder;
import org.lenskit.knn.user.UserSimilarity;
import org.lenskit.knn.user.UserUserItemScorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationService {

    @Autowired
    private IReadBookRepository iReadBookRepository;

    public List<IReadBook> getRecommendation(List<IReadRating> rs, User user) {
        List<Rating> ratings = compileRatings(rs);

        EventDAO dao = new EventCollectionDAO(ratings);
        LenskitConfiguration config = new LenskitConfiguration();
        config.bind(EventDAO.class).to(dao);
        config.bind(ItemScorer.class).to(UserUserItemScorer.class);
        config.bind(NeighborFinder.class).to(LiveNeighborFinder.class);
        config.within(UserSimilarity.class)
                .bind(VectorSimilarity.class)
                .to(PearsonCorrelation.class);

        LenskitRecommenderEngine engine = LenskitRecommenderEngine.build(config);
        LenskitRecommender rec = engine.createRecommender();

        ItemRecommender recommender = rec.getItemRecommender();
        assert recommender != null;

        List<Long> recs = recommender.recommend(user.getId());

        List<IReadBook> rBooks = new ArrayList<>();

        for (Long rId : recs) {
            rBooks.add(iReadBookRepository.findOne(rId));
        }
        return rBooks;
    }

    private List<Rating> compileRatings(List<IReadRating> ratings) {
        List<Rating> rs = new ArrayList<Rating>();

        for (IReadRating r : ratings) {
            rs.add(Rating.create(r.getUser().getId(), r.getBook().getId(), r.getRate()));
        }

        return rs;
    }

}
