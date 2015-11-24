package com.iread.controller;

import com.iread.form.UserSignUpForm;
import com.iread.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class SignUpController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupHandler(ModelMap model) {
        model.addAttribute("userSignUp", new UserSignUpForm());
        return new ModelAndView("registration");
    }

   @RequestMapping(value = "/signup_save", method = RequestMethod.POST)
    public ModelAndView signupHandler(@ModelAttribute("userSignUp") UserSignUpForm userSignUpForm) {
       userService.saveNewUserForm(userSignUpForm);
       return new ModelAndView("catalog");
    }
}
