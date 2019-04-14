package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RatingController {
    @Autowired
    RatingService ratingService;

    @PostMapping("/resourceRating")
    @ResponseBody
    public void resourceRating(@RequestParam("rating") Integer rating, @RequestParam("resourceId") Integer resourceId){
        ratingService.saveRating(rating,resourceId);

    }
}
