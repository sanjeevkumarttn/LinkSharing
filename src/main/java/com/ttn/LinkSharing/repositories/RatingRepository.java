package com.ttn.LinkSharing.repositories;

import com.ttn.LinkSharing.entities.Rating;
import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

    void  deleteRatingByResource(Resource resource);

    Rating findByUserAndResource(User user, Resource resource);
}
