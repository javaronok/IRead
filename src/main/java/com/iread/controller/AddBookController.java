package com.iread.controller;

import com.iread.form.UserAddBookForm;
import com.iread.form.UserSignUpForm;
import com.iread.model.User;
import com.iread.security.UserService;
import com.iread.service.IReadBooksService;
import com.iread.utils.AttachmentFileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/")
public class AddBookController {

    @Autowired
    private IReadBooksService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    protected AttachmentFileStore attachmentFileStore;

    @RequestMapping(value = "/addbook", method = RequestMethod.GET)
    public ModelAndView addbookHandler(ModelMap model) {
        model.addAttribute("userAddBook", new UserAddBookForm());
        return new ModelAndView("add_book");
    }

    @RequestMapping(value = "/book_persist", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String addBookPost(@RequestBody UserAddBookForm userAddBookForm, Principal principal) {
        String userName = principal.getName();
        User user = userService.getUserByName(userName);

        bookService.saveBook(userAddBookForm, user);
        return "200";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String upload(MultipartHttpServletRequest servletRequest) throws IOException {
        MultipartFile file = servletRequest.getFile("file");
        return attachmentFileStore.saveTmpFile(file);
    }
}
