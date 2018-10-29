package com.elitbet.controller;

import com.elitbet.model.User;
import com.elitbet.service.SecurityService;
import com.elitbet.service.UserBankService;
import com.elitbet.service.UserService;
import com.elitbet.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping()
class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private SecurityService securityService;


    @GetMapping(value = "/registration")
    public String registration(Model model){
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        model.addAttribute("userForm",new User());
        return "registration";
    }

    @PostMapping(value="/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        userValidator.validate(userForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        String password = userForm.getPassword();
        userService.createUser(userForm);
        securityService.autologin(userForm.getUsername(),password);
        return "redirect:/welcome";
    }

    @GetMapping(path = "/user/updateBankValue")
    public ModelAndView updateBankValue(@RequestParam int valueUpdate, Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        userBankService.updateBankValue(user.getUserBank(),valueUpdate);
        return new ModelAndView("allbets");
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout, WebRequest webRequest) {
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping(value = {"/","/welcome"})
    public String welcome(Model model){
        return "welcome";
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> findAll(){
        return userService.findAll();
    }
}