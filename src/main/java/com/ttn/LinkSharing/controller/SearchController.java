package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.services.ResourceService;
import com.ttn.LinkSharing.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    TopicService topicService;

    @Autowired
    ResourceService resourceService;

//    @RequestMapping("/autocomplete")
//    public List searchBar(@RequestParam("pattern") String topicOrResource){
//        System.out.println("in autocomplete");
//        // System.out.println("!!!!!!!!!!!!!!1"+topicService.getPublicTopicsByTopicNameSearch(topicOrResource));
//        System.out.println("@@@@@@@@@@@@@@2"+resourceService.getAllResourcesByResourceSearch(topicOrResource));
//        return resourceService.getAllResourcesByResourceSearch(topicOrResource);
//    }

    @GetMapping("/searchTopic/{topic}")
    public String searchForm(@PathVariable("topic") String topicResourceName, Model model) {
        System.out.println(topicResourceName);
        Resource resource = new Resource();
        model.addAttribute("resource", resource);

        model.addAttribute("trendingTopics", topicService.getPublicTopicsByTopicNameSearch(topicResourceName));
        model.addAttribute("resources", resourceService.getAllResourcesByResourceSearch(topicResourceName));

        return "search";
    }

    @RequestMapping("/searchForResources")
    public ModelAndView searchForResources(@RequestParam("search") String value, @RequestParam("topicId") String topicId, Model model, HttpSession session) {
        System.out.println("search topic - " + value + " : " + topicId);
        Topic topic = topicService.getTopicById(Integer.parseInt(topicId));
        List<Resource> resourceList = null;
        if (value.equalsIgnoreCase("download")) {
            resourceList = resourceService.getAllDownloadableResourceTopicSpecific(topic);
        } else if (value.equalsIgnoreCase("link")) {
            resourceList = resourceService.getAllLinkResourceTopicSpecific(topic);
        } else {
            resourceList = resourceService.getAllResourcesByDescAndTopicSearch(value, topic);
        }

        model.addAttribute("searchResultResources", resourceList);
        return new ModelAndView("resourceSearchforTopic");
    }

}
