package com.ttn.LinkSharing.repositories;

import com.ttn.LinkSharing.entities.Subscription;
import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription,Integer> {

    Integer countAllByUser(User user);

    Subscription findByTopicAndUser(Topic topic, User user);

    List<Subscription> findAll();

    Integer deleteAllByTopic(Topic topic);

    ArrayList<Subscription> findAllByTopic(Topic topic);

    ArrayList<Subscription> findAllByUser(User user);
}
