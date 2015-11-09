package com.iread.controller;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import com.iread.security.UserService;
import com.iread.service.IReadBooksService;
import com.iread.service.RatingService;
import com.iread.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RestController
@Controller
public class IReadBookController {

    @Autowired
    private IReadBooksService iReadBooksService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ModelAndView show(ModelMap model, Principal principal) {
        List<IReadBook> books = iReadBooksService.listBooks();

        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            List<IReadRating> iReadRatings = ratingService.getAllRatings();
            List<IReadBook> recms = recommendationService.getRecommendation(iReadRatings, user);
            model.put("recommendations", recms);
        }

        model.put("books", books);
        return new ModelAndView("catalog");
    }
}
