package com.elitbet.controller;

import com.elitbet.model.Client;
import com.elitbet.service.SecurityService;
import com.elitbet.service.ClientBankService;
import com.elitbet.service.ClientService;
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

import javax.jws.WebParam;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping()
class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientBankService clientBankService;
    @Autowired
    private SecurityService securityService;


    @GetMapping(value = "/profile")
    public ModelAndView profile(Authentication authentication){
        Client client = clientService.findByName(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("client", client);
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public String registration(Model model){
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        model.addAttribute("userForm",new Client());
        return "registration";
    }

    @PostMapping(value="/registration")
    public String registration(@ModelAttribute("userForm") Client userForm, BindingResult bindingResult){
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        userValidator.validate(userForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        String password = userForm.getPassword();
        clientService.createClient(userForm);
        securityService.autologin(userForm.getName(),password);
        return "redirect:/welcome";
    }

    @GetMapping(path = "/user/updateBankValue")
    public ModelAndView updateBankValue(@RequestParam int valueUpdate, Authentication authentication){
        Client client = clientService.findByName(authentication.getName());
        clientBankService.updateBankValue(client.getClientBank(),valueUpdate);
        return new ModelAndView("allbets");
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout, WebRequest webRequest) {
        logger.info(String.valueOf(SecurityContextHolder.getContext()));
        if (error != null)
            model.addAttribute("error", "Your name and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping(value = {"/","/welcome"})
    public String welcome(Model model){
        return "redirect:/events";
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public List<Client> findAll(){
        return clientService.findAll();
    }
}