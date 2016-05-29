package com.iread.controller;

import com.iread.form.BookForm;
import com.iread.form.RecommendationResultForm;
import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.security.UserService;
import com.iread.service.CompositeRecommendationService;
import com.iread.service.IReadBooksService;
import com.iread.service.RatingService;
import com.iread.service.cbf.ContentBasedRecommendationService;
import com.iread.service.collabr.CollaborativeRecommendationService;
import com.iread.utils.DataTablesResultSet;
import com.iread.utils.ResultSet;
import com.iread.utils.WebResultSet;
import org.lenskit.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class UserProfileController {
    @Autowired
    private IReadBooksService iReadBooksService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompositeRecommendationService compositeRecommendationService;

    @Autowired
    private CollaborativeRecommendationService collaborativeRecommendationService;

    @Autowired
    private ContentBasedRecommendationService contentBasedRecommendationService;

    @RequestMapping(value = "profile", method = RequestMethod.GET)
    public ModelAndView show(ModelMap model, Principal principal) {
        return new ModelAndView("user_profile");
    }

    @ResponseBody
    @RequestMapping(value = "rating_table_full", method = RequestMethod.GET, produces = "application/json")
    public WebResultSet<BookForm> ratingTableFull(Principal principal) {
        if (principal != null) {
            List<BookForm> all = new ArrayList<>();

            User user = userService.getUserByName(principal.getName());
            List<IReadRating> userRatings = ratingService.listRatingsByUser(user);

            for (IReadRating r : userRatings) {
                IReadBook book = r.getBook();

                BookForm bf = new BookForm();
                bf.setBook(book);
                bf.setOwnRating(r.getRate());
                bf.setAvgRating(ratingService.avgBookRating(book));

                all.add(bf);
            }

            ResultSet<BookForm> rs = new ResultSet<>(all, (long) all.size(), all.size());
            return new DataTablesResultSet<>(rs);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "composite_table_full", method = RequestMethod.GET, produces = "application/json")
    public WebResultSet<RecommendationResultForm> compositeTableFull(Principal principal) {
        if (principal != null) {
            User user = userService.getUserByName(principal.getName());

            List<IReadBook> books = iReadBooksService.listBooks();
            List<IReadRating> iReadRatings = ratingService.getAllRatings();

            List<Result> results = compositeRecommendationService.getCompositeRecommendations(books, iReadRatings, user);

            Set<RecommendationResultForm> all = new LinkedHashSet<>();

            for (Result r : results) {
                IReadBook book = iReadBooksService.load(r.getId());

                RecommendationResultForm rf = new RecommendationResultForm();
                rf.setBook(book);
                rf.setAvgRating(ratingService.avgBookRating(book));
                rf.setScore(new BigDecimal(r.getScore()));

                all.add(rf);
            }

            ResultSet<RecommendationResultForm> rs = new ResultSet<>(new ArrayList<>(all), (long) all.size(), all.size());
            return new DataTablesResultSet<>(rs);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "cbf_table_full", method = RequestMethod.GET, produces = "application/json")
    public WebResultSet<RecommendationResultForm> cbfTableFull(Principal principal) {
        if (principal != null) {
            User user = userService.getUserByName(principal.getName());

            List<IReadBook> books = iReadBooksService.listBooks();
            List<IReadRating> iReadRatings = ratingService.getAllRatings();

            List<Result> results = contentBasedRecommendationService.getRecommendation(books, iReadRatings, user);

            List<RecommendationResultForm> all = new ArrayList<>();

            for (Result r : results) {
                IReadBook book = iReadBooksService.load(r.getId());

                RecommendationResultForm rf = new RecommendationResultForm();
                rf.setBook(book);
                rf.setAvgRating(ratingService.avgBookRating(book));
                rf.setScore(new BigDecimal(r.getScore()));

                all.add(rf);
            }

            ResultSet<RecommendationResultForm> rs = new ResultSet<>(all, (long) all.size(), all.size());
            return new DataTablesResultSet<>(rs);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "collaborative_table_full", method = RequestMethod.GET, produces = "application/json")
    public WebResultSet<RecommendationResultForm> collaborativeTableFull(Principal principal) {
        if (principal != null) {
            User user = userService.getUserByName(principal.getName());

            List<IReadRating> iReadRatings = ratingService.getAllRatings();

            List<Result> results = collaborativeRecommendationService.getRecommendation(iReadRatings, user);

            List<RecommendationResultForm> all = new ArrayList<>();

            for (Result r : results) {
                IReadBook book = iReadBooksService.load(r.getId());

                RecommendationResultForm rf = new RecommendationResultForm();
                rf.setBook(book);
                rf.setAvgRating(ratingService.avgBookRating(book));
                rf.setScore(new BigDecimal(r.getScore()));

                all.add(rf);
            }

            ResultSet<RecommendationResultForm> rs = new ResultSet<>(all, (long) all.size(), all.size());
            return new DataTablesResultSet<>(rs);
        } else {
            return null;
        }
    }
}
