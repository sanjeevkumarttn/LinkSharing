package com.ttn.LinkSharing.services;

import com.ttn.LinkSharing.entities.Rating;
import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    HttpSession session;
    @Autowired
    ResourceService resourceService;

    public void saveRating(Integer userRating, Integer resourceId){
        User user = (User)session.getAttribute("user");
        Resource resource = resourceService.getResourceById(resourceId);
        Rating rating = ratingRepository.findByUserAndResource(user,resource);
        if(rating==null) {
            System.out.println("insde rating if");
            rating = new Rating();
            rating.setPoints(userRating);
            rating.setResource(resourceService.getResourceById(resourceId));
            rating.setUser((User) session.getAttribute("user"));
            ratingRepository.save(rating);
        }else {
            System.out.println("insde rating else");
            rating.setPoints(userRating);
            ratingRepository.save(rating);
        }
    }

    @Transactional
    public  void deleteRating(Topic topic){
        List<Resource> resourceList = resourceService.getResourceByTopic(topic);
        if(!resourceList.isEmpty()) {
            Iterator<Resource> resourceIterator = resourceList.iterator();
            while (resourceIterator.hasNext())
                ratingRepository.deleteRatingByResource(resourceIterator.next());
        }
    }
}
