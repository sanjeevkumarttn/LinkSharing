package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.ResourceService;
import com.ttn.LinkSharing.services.SubscriptionService;
import com.ttn.LinkSharing.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    HttpSession session;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    ResourceService resourceService;

    @PostMapping(value = "/createTopic")
    public ModelAndView createTopic(@RequestParam("topicName") String topicname,
                              @RequestParam("visibility") String visibility,
                              RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Topic topicCreated = topicService.addTopic(topicname, visibility);

        if (topicCreated != null) {
            redirectAttributes.addFlashAttribute("message", "Your Topic Has been Created");
        } else {
            redirectAttributes.addFlashAttribute("message", "some problem occurs in creating topic");
        }
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }


    @GetMapping("/checkTopicNameUnique")
    @ResponseBody
    public boolean checkTopicNameUnique(@RequestParam("topicName") String topicName) {

        Boolean result;
        System.out.println("topic name : "+topicName);
        result = topicService.checkTopicNameUnique(topicName);

        return result;
    }

    @GetMapping("/deleteTopic/{id}")
    public ModelAndView deleteTopicById(@PathVariable("id") Integer topicId, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Topic topic = topicService.getTopicById(topicId);
        topicService.deleteTopic(topic);

        redirectAttributes.addFlashAttribute("message","Topic Deleted..");

        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @PostMapping("/changeTopicname")
    @ResponseBody
    public String changeTopicname(@RequestParam Integer topicId,@RequestParam String newname){
        System.out.println(topicId+" : "+newname);
        String result = topicService.changeTopicName(topicId,newname);
        if(result==null)
            return "redirect:/dashboard";
        return result;
    }

    //for topic page
    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable Integer id, Model model){

        Topic topic = topicService.getTopicById(id);
        List<User> userSubscribedToTopic = subscriptionService.getAllUsersByTopic(topic);
        List<Resource> resourceListOfTopic = resourceService.getResourcesByTopic(topic);
        HashMap<Integer,Integer> subscriptionCountOfUsersList = subscriptionService.getSubscriptionCountOfUsersList(userSubscribedToTopic);
        model.addAttribute("subscriptionDetails",subscriptionService.getAllSubscriptionDetailsByTopic(topic));
        model.addAttribute("topic",topic);
        model.addAttribute("usersSubscribed",userSubscribedToTopic);
        model.addAttribute("subscriptionCountOfUsers",subscriptionCountOfUsersList);
        model.addAttribute("resources",resourceListOfTopic);
        model.addAttribute("allTopics",topicService.getAllTopics());

        return "topic";
    }


    @PostMapping("/changeVisibility")
    @ResponseBody
    public String changeVisibility(@RequestParam("newVisibility") String visibility, @RequestParam("topicId") Integer topicId){
        Topic topic = topicService.getTopicById(topicId);

        User user = (User) session.getAttribute("user");

        if (topic == null || topic.getUser().getUserId() != user.getUserId()) {
            return null;
        }
        else {
            topicService.changeVisibility(topic,visibility);
            return visibility;
        }
    }

    @GetMapping("/viewAllTopics")
    public String viewAllTopicsPage(Model model){
        model.addAttribute("allTopics",topicService.getAllTopics());
        return "allTopics";
    }

}
