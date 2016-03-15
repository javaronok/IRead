package com.iread.service;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.repository.IReadBookRepository;
import com.iread.service.cbf.ContentBasedRecommendationService;
import com.iread.service.collabr.CollaborativeRecommendationService;
import org.lenskit.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CompositeRecommendationService {

    @Autowired
    private IReadBookRepository iReadBookRepository;

    @Autowired
    private CollaborativeRecommendationService collaborativeRecommendationService;

    @Autowired
    private ContentBasedRecommendationService contentBasedRecommendationService;

    public List<IReadBook> getRecommendation(List<IReadBook> books, List<IReadRating> rs, User user) {
        List<Result> results = new ArrayList<>();

        results.addAll(collaborativeRecommendationService.getRecommendation(rs, user));
        results.addAll(contentBasedRecommendationService.getRecommendation(books, rs, user));

        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return o1.getScore() == o2.getScore() ? 0 : o1.getScore() < o2.getScore() ? 1 : -1;
            }
        });

        Set<IReadBook> rBooks = new LinkedHashSet<>();

        for (Result r : results) {
            if (r.getScore() > 0) {
                rBooks.add(iReadBookRepository.findOne(r.getId()));
            }
        }
        return new ArrayList<>(rBooks);
    }
}
