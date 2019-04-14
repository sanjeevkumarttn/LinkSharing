package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.SubscriptionService;
import com.ttn.LinkSharing.services.TopicService;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class EditProfileController {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    TopicService topicService;

    @GetMapping("/editProfile")
    public String editProfile() {
        session.setAttribute("updatePwdMsg", null);
        session.setAttribute("updateProfileMsg", null);

        session.setAttribute("topicSubscribed", subscriptionService.getSubscriptionCountOfUser((User) session.getAttribute("user")));
        session.setAttribute("topicsCreated", topicService.getTopicsCreatedCountOfUser((User) session.getAttribute("user")));
        return "editProfile";
    }

  /*  @PostMapping("/changePassword")
    public String changePassword(@RequestParam("password") String password) {
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        int status = userService.resetPassword(password, email);

        if (status > 0)
            session.setAttribute("updatePwdMsg", "Password Changed..!");
        else
            session.setAttribute("updatePwdMsg", "Password not changed..! Please try again..");

        return "editprofile";
    }*/

    @PostMapping("/editUserInfo")
    public ModelAndView editUserDetails(@RequestParam("firstName") String firstname,
                                        @RequestParam("lastName") String lastname,
                                        @RequestParam("userName") String username,
                                        @RequestParam("image") MultipartFile file,
                                        RedirectAttributes redirectAttributes) {
        String previousUserName = null;
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();

        if (!firstname.isEmpty())
            user.setFirstName(firstname);
        if (!lastname.isEmpty())
            user.setLastName(lastname);
        if (!username.isEmpty()) {
            previousUserName = user.getUserName();
            //System.out.println("previouse user: "+previousUserName);
            user.setUserName(username);
        }

        int status = userService.updateUser(user, file, previousUserName);

        if (status > 0)
            redirectAttributes.addFlashAttribute("message", "Changes Updated..!");
        else
            redirectAttributes.addFlashAttribute("message", "Changes not Updated..! Please try again..");

        modelAndView.setViewName("redirect:/editProfile");
        return modelAndView;
    }


    @GetMapping("/checkUsernameAvailabilityToEdit")
    @ResponseBody
    public Boolean checkUsernameAvailability(@RequestParam String uname) {
        //System.out.println("in controller checkUsernameAvailabilityToEdit");
        Boolean result;
        System.out.println("checkUsernameAvailabilityToEdit : " + uname);
        result = userService.doesUsernameExistsExceptThis(uname);
        return result;
    }

    @PostMapping("/resetPasswordInEditProfile")
    public ModelAndView setPassword(@RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        int status = userService.resetPassword(password, user.getEmail());

        if (status > 0) {
            System.out.println("inside if");
            redirectAttributes.addFlashAttribute("message", "Password Changed..!");
        } else
            redirectAttributes.addFlashAttribute("message", "Issue to Updated..! Please try again..");

        modelAndView.setViewName("redirect:/editProfile");
        return modelAndView;
    }
}
