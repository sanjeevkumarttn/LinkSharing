package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class UserRegistrationController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("image") MultipartFile file,
                               Model model) {
        User user1 = userService.addUser(user, file);
        if (user1 == null) {
            return "redirect:/";
        }
        session.setAttribute("user", user1);
        return "redirect:/dashboard";
    }

    @GetMapping("/isEmailRegistered")
    @ResponseBody
    public boolean checkEmailRegistration(String email) {
        return userService.doesEmailExist(email);
    }

    @GetMapping("/isUserNameRegistered")
    @ResponseBody
    public boolean checkUserNameRegistration(String userName) {
        return userService.doesUserNameExist(userName);
    }

}
