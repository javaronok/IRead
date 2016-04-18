package com.iread.controller;

import com.iread.form.BookForm;
import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.IReadTag;
import com.iread.model.User;
import com.iread.security.UserService;
import com.iread.service.CompositeRecommendationService;
import com.iread.service.IReadBooksService;
import com.iread.service.IReadTagService;
import com.iread.service.RatingService;
import com.iread.service.collabr.CollaborativeRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class IReadBookController {

    @Autowired
    private IReadBooksService iReadBooksService;

    @Autowired
    private IReadTagService iReadTagsService;

    @Autowired
    private CompositeRecommendationService compositeRecommendationService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "catalog", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request,
                             ModelMap model, Principal principal
    ) {
        List<IReadBook> books = iReadBooksService.listBooks();
        List<BookForm> result = new ArrayList<>();
        for (IReadBook book : books) {
            BookForm form = new BookForm();
            form.setBook(book);
            form.setAvgRating(ratingService.avgBookRating(book));

            result.add(form);
        }

        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            List<IReadRating> iReadRatings = ratingService.getAllRatings();
            List<IReadBook> recms = compositeRecommendationService.getRecommendation(books, iReadRatings, user);
            model.put("recommendations", recms);
        }

        // Получение ошибки из сессии
        HttpSession session = request.getSession();
        Exception authException = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (authException != null) {
            model.put("errorMessage", "Неверный логин/пароль");
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, null);
        }

        model.put("books", result);
        return new ModelAndView("catalog");
    }

    /*@RequestMapping(value = "books", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IReadBook> listBooks() {
        List<IReadBook> books = iReadBooksService.listBooks();
        return books;
    }*/

    @RequestMapping(value = "rating", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String postBookRating(@RequestParam("book") IReadBook book,
                                 @RequestParam("rate") BigDecimal rate,
                                 Principal principal
    ) throws IllegalAccessException {
        if (principal == null)
            throw new IllegalAccessException();

        String userName = principal.getName();
        User user = userService.getUserByName(userName);

        ratingService.postBookRating(user, book, rate.intValue());
        return HttpStatus.OK.getReasonPhrase();
    }

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    @ResponseBody
    public List<IReadTag> getTags(Principal principal) throws IllegalAccessException {
        List<IReadTag> tags = iReadTagsService.listTags();
        return tags;
    }
}
