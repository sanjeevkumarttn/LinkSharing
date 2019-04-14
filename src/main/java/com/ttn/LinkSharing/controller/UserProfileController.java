package com.ttn.LinkSharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {
    @GetMapping("/userProfile")
    public String userProfile(){
        return "userProfile";
    }

}
