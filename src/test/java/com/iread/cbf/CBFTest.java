package com.iread.cbf;

import com.iread.service.cbf.IReadItemTagDAO;
import com.iread.service.cbf.IReadUserDAO;
import com.iread.service.cbf.ItemTags;
import com.iread.service.cbf.TFIDFItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.junit.Test;
import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.api.ItemRecommender;
import org.lenskit.api.ItemScorer;
import org.lenskit.api.Recommender;
import org.lenskit.api.RecommenderBuildException;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.dao.ItemDAO;
import org.lenskit.data.dao.UserDAO;
import org.lenskit.data.ratings.Rating;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Dmitriy on 03.03.2016.
 */
public class CBFTest {

    @Test
    public void mainTest() throws RecommenderBuildException {
        List<Rating> rs = new ArrayList<>();
        rs.add(createRating(1, 1, 5));
        rs.add(createRating(1, 2, 4));
        rs.add(createRating(1, 3, 3));
        rs.add(createRating(2, 1, 4));
        rs.add(createRating(2, 3, 5));
        rs.add(createRating(3, 2, 3));
        rs.add(createRating(3, 3, 5));
        rs.add(createRating(3, 5, 4));
        rs.add(createRating(4, 4, 3));
        rs.add(createRating(4, 5, 4));
        rs.add(createRating(5, 3, 4));
        rs.add(createRating(5, 4, 2));
        rs.add(createRating(5, 5, 4));
        rs.add(createRating(6, 1, 3));
        rs.add(createRating(6, 3, 3));
        rs.add(createRating(6, 6, 5));

        Collection<ItemTags> tags = new ArrayList<ItemTags>();
        tags.add(ItemTags.createItemTags(1L, "A", "D", "D"));
        tags.add(ItemTags.createItemTags(2L, "B", "C", "C"));
        tags.add(ItemTags.createItemTags(3L, "B", "B", "B"));
        tags.add(ItemTags.createItemTags(4L, "A", "A", "A"));
        tags.add(ItemTags.createItemTags(5L, "B", "B"));
        tags.add(ItemTags.createItemTags(6L, "C", "C", "C", "D"));

        LenskitConfiguration config = configureRecommender(rs, tags);

        Recommender rec = LenskitRecommender.build(config);

        ItemRecommender irec = rec.getItemRecommender();

        for (Long userId : createUserCollection(rs)) {
            List<Long> rcs = irec.recommend(userId);
            System.out.println("User ID: " + userId + ", recommendations: " + rcs);
        }

    }

    public static Rating createRating(int userId, int itemId, int value) {
        return Rating.create(userId, itemId, value);
    }

    private static LenskitConfiguration configureRecommender(List<Rating> rs, Collection<ItemTags> tags) {
        LenskitConfiguration config = new LenskitConfiguration();

        EventDAO dao = new EventCollectionDAO(rs);

        // configure the rating data source
        config.bind(EventDAO.class).to(dao);

        // use custom item and user DAOs
        // specify item DAO implementation with tags
        config.bind(ItemDAO.class)
                .to(new IReadItemTagDAO(createItemCollection(rs), tags));
        // specify tag file
        /*config.set(TagFile.class)
                .to());*/
        // and title file
        /*config.set(TitleFile.class)
                .to();*/

        // our user DAO can look up by user name
        config.bind(UserDAO.class)
                .to(new IReadUserDAO(createUserCollection(rs)));
        /*config.set(UserFile.class)
                .to();*/

        // use the TF-IDF scorer you will implement to score items
        config.bind(ItemScorer.class)
                .to(TFIDFItemScorer.class);
        return config;
    }

    public static Collection<Long> createItemCollection(List<Rating> rs) {
        Collection<Long> itemCollection = new TreeSet<Long>();
        for (Rating r : rs) {
            itemCollection.add(r.getItemId());
        }
        return itemCollection;
    }

    public static Collection<Long> createUserCollection(List<Rating> rs) {
        Collection<Long> userCollection = new TreeSet<Long> ();
        for (Rating r : rs) {
            userCollection.add(r.getUserId());
        }
        return userCollection;
    }
}
