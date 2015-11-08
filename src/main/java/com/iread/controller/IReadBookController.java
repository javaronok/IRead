package com.iread.controller;

import com.iread.model.IReadBook;
import com.iread.service.IReadBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RestController
@Controller
public class IReadBookController {

    @Autowired
    private IReadBooksService iReadBooksService;

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ModelAndView show(ModelMap model, Principal principal) {
        List<IReadBook> books = iReadBooksService.listBooks();
        model.put("books", books);
        return new ModelAndView("catalog");
    }
}
