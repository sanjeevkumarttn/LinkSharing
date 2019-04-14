package com.ttn.LinkSharing.repositories;

import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.entities.Visibility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Topic findByTopicNameAndUser(String topicname, User user);

    Integer countAllByUser(User user);

    List<Topic> findBySubscribers(Integer id);
    /*@Query("SELECT t FROM Topic t WHERE t.user = user")
    public List<Topic> findAllTopic(User user);*/

    List<Topic> findByUser(User user);

    // for post page
    List<Topic> findAllByVisibility(Visibility visible);

    Boolean deleteByTopicId(Integer id);

    List<Topic> findAllByVisibilityAndTopicNameLike(Visibility visibility,String topicname);


}
