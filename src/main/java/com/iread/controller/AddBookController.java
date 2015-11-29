package com.iread.controller;

import com.iread.form.UserAddBookForm;
import com.iread.form.UserSignUpForm;
import com.iread.security.UserService;
import com.iread.service.IReadBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class AddBookController {

    @Autowired
    private IReadBooksService bookService;

    @RequestMapping(value = "/addbook", method = RequestMethod.GET)
    public ModelAndView addbookHandler(ModelMap model) {
        model.addAttribute("userAddBook", new UserAddBookForm());
        return new ModelAndView("add_book");
    }

    @RequestMapping(value = "/book_save", method = RequestMethod.POST)
    public RedirectView addBookHandler(@ModelAttribute("userAddBook") UserAddBookForm userAddBookForm) {
        bookService.saveBook(userAddBookForm);
        return new RedirectView("catalog");
    }
}
