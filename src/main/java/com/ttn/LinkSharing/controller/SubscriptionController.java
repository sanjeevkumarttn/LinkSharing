package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.SubscriptionService;
import com.ttn.LinkSharing.services.TopicService;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class SubscriptionController {

    @Autowired
    HttpSession session;

    @Autowired
    TopicService topicService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    UserService userService;

    @PostMapping("/changeSubscription")
    public String changeSubscriptionType(@RequestParam("topicId") String topicId, @RequestParam("newseriousness") String seriousness, @RequestParam("btn") String btnType) {
        System.out.println("in changeSubscription");
        System.out.println("changeSubscription: " + seriousness);
        if (btnType.equals("subscribe"))
            return "redirect:/subscribeFromDashboard/" + topicId + "/" + seriousness;
        else
            return "redirect:/unsubscribeFromDashboard/" + topicId;
    }

    @RequestMapping("/subscribeFromDashboard/{topicId}/{seriousness}")
    public String subscribeFromDashboard(@PathVariable("topicId") String topicId, @PathVariable("seriousness") String seriousness) {
        System.out.println("subscribeFromDashboard :" + seriousness);
        Topic topic = topicService.getTopicById(Integer.parseInt(topicId));
        User user = (User) session.getAttribute("user");
        if (topic == null)
            return "redirect:/dashboard";
        else {

            topicService.addSubscriber(topic, user, seriousness);
            return "redirect:/dashboard";
        }
    }

    @RequestMapping("/unsubscribeFromDashboard/{topicId}")
    public String unsubscribeFromDashboard(@PathVariable("topicId") String topicId) {
        Topic topic = topicService.getTopicById(Integer.parseInt(topicId));
        User user = (User) session.getAttribute("user");
        if (topic == null)
            return "redirect:/dashboard";
        else {
            topicService.removeSubscriber(topic, user);
            return "redirect:/dashboard";
        }
    }


    @PostMapping("/changeSeriousness")
    @ResponseBody
    public String changeSeriousness(@RequestParam("newseriousness") String seriousness, @RequestParam("topicId") String topicId, HttpSession session) {
        //System.out.println(topicId);
        Topic topic = topicService.getTopicById(Integer.parseInt(topicId));
        User user = (User) session.getAttribute("user");
        if (topic == null || topic.getUser().getUserId() == user.getUserId()) {
            //System.out.println("inside if");
            return null;
        } else {
            //System.out.println("inside else");
            subscriptionService.changeSeriousness(topic, user, seriousness);
            return seriousness;
        }
    }


    @RequestMapping("/subscribeFromLink")
    public ModelAndView subscribeFromLink(@RequestParam("topicId") String topicId,
                                    @RequestParam("userId") Integer userId, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();
        Topic topic = topicService.getTopicById(Integer.parseInt(topicId));
        User user = userService.getUserById(userId);
        topicService.addSubscriber(topic, user, "VERY_SERIOUS");
        redirectAttributes.addFlashAttribute("message","Topic has been subscribed..! Please login to see..!");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}


