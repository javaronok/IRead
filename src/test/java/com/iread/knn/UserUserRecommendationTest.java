package com.iread.knn;

import org.grouplens.lenskit.vectors.similarity.CosineVectorSimilarity;
import org.grouplens.lenskit.vectors.similarity.PearsonCorrelation;
import org.grouplens.lenskit.vectors.similarity.SpearmanRankCorrelation;
import org.grouplens.lenskit.vectors.similarity.VectorSimilarity;
import org.junit.Test;
import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.LenskitRecommenderEngine;
import org.lenskit.api.ItemRecommender;
import org.lenskit.api.ItemScorer;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.ratings.Rating;
import org.lenskit.knn.user.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 28.02.2016.
 */
public class UserUserRecommendationTest {

    @Test
    public void recommendationTest() {
        List<Rating> rs = new ArrayList<Rating>();
        rs.add(Rating.create(1, 1, 5));
        rs.add(Rating.create(1, 2, 4));
        rs.add(Rating.create(1, 3, 5));
        rs.add(Rating.create(2, 1, 4));
        rs.add(Rating.create(2, 3, 5));
        rs.add(Rating.create(3, 2, 3));
        rs.add(Rating.create(3, 3, 5));
        rs.add(Rating.create(3, 5, 4));
        rs.add(Rating.create(4, 4, 3));
        rs.add(Rating.create(4, 5, 4));
        rs.add(Rating.create(5, 3, 4));
        rs.add(Rating.create(5, 4, 2));
        rs.add(Rating.create(5, 5, 4));
        rs.add(Rating.create(6, 1, 3));
        rs.add(Rating.create(6, 6, 5));

        for (long uuid = 1; uuid < 7; uuid++) {
            List<Long> recs = getRecommendations(rs, uuid);
            System.out.println("UID: " + uuid + ", recommendations: " + recs);
        }
    }

    private static List<Long> getRecommendations(List<Rating> rs, Long user) {
        EventDAO dao = new EventCollectionDAO(rs);
        LenskitConfiguration config = new LenskitConfiguration();
        config.bind(EventDAO.class).to(dao);
        config.bind(ItemScorer.class).to(UserUserItemScorer.class);
        config.bind(NeighborFinder.class).to(SnapshotNeighborFinder.class);
        config.within(UserSimilarity.class)
                .bind(VectorSimilarity.class)
                .to(CosineVectorSimilarity.class);

        LenskitRecommenderEngine engine = LenskitRecommenderEngine.build(config);
        LenskitRecommender rec = engine.createRecommender();

        ItemRecommender recommender = rec.getItemRecommender();
        assert recommender != null;

        return recommender.recommend(user);
    }
}
