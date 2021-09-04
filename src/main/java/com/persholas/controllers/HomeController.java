package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.IUserRepo;
import com.persholas.models.AuthGroup;
import com.persholas.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("clearview")
@Slf4j
public class HomeController {

    IUserRepo userRepo;
    IAuthGroupRepo authGroupRepo;

    @Autowired
    public HomeController(IUserRepo userRepo, IAuthGroupRepo authGroupRepo)
    {
        this.userRepo = userRepo;
        this.authGroupRepo = authGroupRepo;
    }

    @GetMapping({"/","/home"})
    public String goHome()
    {
        log.warn("executing: HomePage");
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        User newUser = new User();
        model.addAttribute("user",newUser);
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             Model model)
    {
        log.warn(user.toString());
        if(bindingResult.hasErrors())
        {
            return "signup";
        }

        if(authGroupRepo.findByaUsername(user.getEmail()).isEmpty())
        {
            authGroupRepo.save(new AuthGroup(user.getEmail(), "ROLE_CUSTOMER"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPass = encoder.encode(user.getUPassword());
            user.setUPassword(encodedPass);
            userRepo.save(user);
            return "home";
        }

        String usernameExists = "Email is already in our database";
        model.addAttribute("usernameExists",usernameExists);
        return "signup";
    }
}