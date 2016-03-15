package com.iread.service.cbf;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.IReadTag;
import com.iread.model.User;
import com.iread.service.RatingRecommendationService;
import org.lenskit.LenskitConfiguration;
import org.lenskit.LenskitRecommender;
import org.lenskit.api.*;
import org.lenskit.data.dao.EventCollectionDAO;
import org.lenskit.data.dao.EventDAO;
import org.lenskit.data.dao.ItemDAO;
import org.lenskit.data.dao.UserDAO;
import org.lenskit.data.ratings.Rating;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContentBasedRecommendationService extends RatingRecommendationService {
    public List<Result> getRecommendation(List<IReadBook> books, List<IReadRating> rs, User user) {
        List<Rating> ratings = compileRatings(rs);

        List<ItemTags> tags = compileTags(books);

        EventDAO dao = new EventCollectionDAO(ratings);
        LenskitConfiguration config = new LenskitConfiguration();
        config.bind(EventDAO.class).to(dao);
        config.bind(ItemDAO.class).to(new IReadItemTagDAO(createItemCollection(ratings), tags));
        // our user DAO can look up by user name
        config.bind(UserDAO.class).to(new IReadUserDAO(createUserCollection(ratings)));
        // use the TF-IDF scorer you will implement to score items
        config.bind(ItemScorer.class).to(TFIDFItemScorer.class);

        Recommender rec = LenskitRecommender.build(config);

        ItemRecommender irec = rec.getItemRecommender();

        ResultList results = irec.recommendWithDetails(user.getId(), 100, null, null);
        return results;
    }

    /**
     * Collect Tags structure model for Lenskit recommender work
     */
    private List<ItemTags> compileTags(List<IReadBook> books) {
        List<ItemTags> tags = new ArrayList<>();

        for (IReadBook book : books) {
            Long item = book.getId();
            Set<IReadTag> bookTags = book.getTags();
            if (bookTags != null) {
                List<String> bTagNames = compileBookTags(bookTags);
                ItemTags tag = ItemTags.createItemTags(item, bTagNames);
                tags.add(tag);
            }
        }
        return tags;
    }

    private List<String> compileBookTags(Set<IReadTag> bookTags) {
        List<String> result = new ArrayList<>();
        for (IReadTag tag : bookTags) {
            result.add(tag.getTagName());
        }
        return result;
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
