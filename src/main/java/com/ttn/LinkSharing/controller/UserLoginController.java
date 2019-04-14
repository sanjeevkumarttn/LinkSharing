package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserLoginController {

    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;


    @PostMapping("/userLogin")
    public ModelAndView userLogin(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                  RedirectAttributes redirectAttributes) {
        User user = userService.isValidUser(userName, password);
        ModelAndView modelAndView = new ModelAndView();
        if (user == null) {
            redirectAttributes.addFlashAttribute("message","Invalid Username or Password..!");
            modelAndView.setViewName("redirect:/");
            return modelAndView;

        }
        else if(!user.getIsActive())
        {
            redirectAttributes.addFlashAttribute("message","Invalid Username or Password..!");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        session.setAttribute("user", user);
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/logoutUser")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        session.invalidate();
        return "redirect:/";
    }


}

