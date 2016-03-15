package com.iread.service.collabr;

import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.service.RatingRecommendationService;
import org.grouplens.lenskit.vectors.similarity.CosineVectorSimilarity;
import org.grouplens.lenskit.vectors.similarity.VectorSimilarity;
import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.LenskitRecommenderEngine;
import org.lenskit.api.ItemRecommender;
import org.lenskit.api.ItemScorer;
import org.lenskit.api.Result;
import org.lenskit.api.ResultList;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.ratings.Rating;
import org.lenskit.knn.user.LiveNeighborFinder;
import org.lenskit.knn.user.NeighborFinder;
import org.lenskit.knn.user.UserSimilarity;
import org.lenskit.knn.user.UserUserItemScorer;
import org.lenskit.results.BasicResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollaborativeRecommendationService extends RatingRecommendationService {

    public List<Result> getRecommendation(List<IReadRating> rs, User user) {
        List<Rating> ratings = compileRatings(rs);

        EventDAO dao = new EventCollectionDAO(ratings);
        LenskitConfiguration config = new LenskitConfiguration();
        config.bind(EventDAO.class).to(dao);
        config.bind(ItemScorer.class).to(UserUserItemScorer.class);
        config.bind(NeighborFinder.class).to(LiveNeighborFinder.class);
        config.within(UserSimilarity.class)
                .bind(VectorSimilarity.class)
                .to(CosineVectorSimilarity.class);

        LenskitRecommenderEngine engine = LenskitRecommenderEngine.build(config);
        LenskitRecommender rec = engine.createRecommender();

        ItemRecommender recommender = rec.getItemRecommender();
        assert recommender != null;

        ResultList recs = recommender.recommendWithDetails(user.getId(), 100, null, null);
        return normalize(recs, 3.5, 5);
    }

    private List<Result> normalize (List<Result> src, double offset, double threshold) {
       List<Result> results = new ArrayList<>();
        for (Result item : src) {
            double x = (item.getScore()-offset)/(threshold - offset); // Функция нормализации имени Ангелины
            Long bookId = item.getId();
            Result result = new BasicResult(bookId, x);
            results.add(result);
        }
        return results;
    }
}
