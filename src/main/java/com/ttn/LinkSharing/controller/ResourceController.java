package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.services.ResourceService;
import com.ttn.LinkSharing.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class ResourceController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    HttpSession session;
    @Autowired
    TopicService topicService;

    @GetMapping("/saveLinkResources")
    public ModelAndView addResources(@Param("url") String url, @Param("description") String description,
                                     @Param("resourceDropdown") String resourceDropdown,
                                     RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Resource resource = resourceService.saveLinkResources(url, description, resourceDropdown);
        if (resource != null) {
            redirectAttributes.addFlashAttribute("message", "Resources is added");

        } else {
            redirectAttributes.addFlashAttribute("message", "Error While Creating Resources");

        }
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @PostMapping("/saveDocumentResources")
    public ModelAndView addDownloadResources(@Param("description") String description,
                                             @Param("resourceDropdown") String resourceDropdown,
                                             HttpServletResponse response,
                                             @RequestParam("fileForDownload") MultipartFile file,
                                             RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Resource resource = resourceService.saveDownloadResources(file, description, resourceDropdown);
        if (resource != null) {
            redirectAttributes.addFlashAttribute("message", "Resources is added");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error While Creating Resources");
        }
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }


    @GetMapping("/post")
    public String getPost(Model model){

        User user = (User) session.getAttribute("user");
        model.addAttribute("resourcesSubscribed", resourceService.getResourcesOfsubscribedTopics(user));
        model.addAttribute("trendingTopics", topicService.getAllTopics());

        return "post";
    }

    @PostMapping("/editResource")
    public String editResource(@RequestParam("resourceId") Integer resourceId, @RequestParam("description") String description, Model model) {

        System.out.println("1233"+resourceId);
        Resource resource = resourceService.updateResourceDescription(resourceId, description);

        return "redirect:/post";
    }
}

