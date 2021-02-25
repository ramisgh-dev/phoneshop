package com.sip.ams.controllers;


import javax.validation.Valid;
import com.sip.ams.entities.Users;
import com.sip.ams.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    
    @RequestMapping(value={"/home"}, method = RequestMethod.GET)
    public ModelAndView accueil(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }
 
    
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Users users = new Users();
        modelAndView.addObject("user", users);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Users users, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Users userExists = userService.findUserByEmail(users.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(users);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new Users());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
