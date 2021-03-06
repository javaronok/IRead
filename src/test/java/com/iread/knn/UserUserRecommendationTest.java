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
import org.lenskit.api.Result;
import org.lenskit.api.ResultList;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.ratings.Rating;
import org.lenskit.knn.user.*;
import org.lenskit.results.BasicResult;

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
            List<Result> recs = getRecommendations(rs, uuid);
            StringBuilder sb = new StringBuilder();
            for (Result r : recs) {
               sb.append(", ").append("score(").append(r.getId()).append(")").append(" = ").append(r.getScore());
            }
            String log = "[" + sb.substring(2) + "]";
            System.out.println("UID: " + uuid + ", recommendations: " + log);
        }

        System.out.println("After normalize: ");

        for (long uuid = 1; uuid < 7; uuid++) {
            List<Result> recs = normalize(getRecommendations(rs, uuid), 3.5, 5);
            StringBuilder sb = new StringBuilder();
            for (Result r : recs) {
               sb.append(", ").append("score(").append(r.getId()).append(")").append(" = ").append(r.getScore());
            }
            String log = "[" + sb.substring(2) + "]";
            System.out.println("UID: " + uuid + ", recommendations: " + log);
        }
    }

    private static ResultList getRecommendations(List<Rating> rs, Long user) {
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

        return recommender.recommendWithDetails(user, 100, null, null);
    }


    private static List<Result> normalize (List<Result> src, double offset, double threshold) {
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
