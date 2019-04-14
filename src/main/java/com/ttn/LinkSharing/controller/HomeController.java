package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Seriousness;
import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.repositories.TopicRepository;
import com.ttn.LinkSharing.services.ResourceService;
import com.ttn.LinkSharing.services.SubscriptionService;
import com.ttn.LinkSharing.services.TopicService;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    TopicService topicService;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ResourceService resourceService;

    @GetMapping("/")
    public String firstPage(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "index";
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        session.setAttribute("topicSubscribed",topicService.getSubscribedTopicsByUSer());
        session.setAttribute("topicSubscribedCount",subscriptionService.getSubscriptionCountOfUser((User)session.getAttribute("user")));
        session.setAttribute("topicsCreatedCount",topicService.getTopicsCreatedCountOfUser((User)session.getAttribute("user")));
        session.setAttribute("trendingTopics",topicService.getAllTopics());
        session.setAttribute("allUserTopics",topicService.userTopics());
        session.setAttribute("seriousnessTypes", Seriousness.values());
        session.setAttribute("AllSubscriptionTopic", subscriptionService.getAllSubscription());

        return "dashboard";
    }



}
