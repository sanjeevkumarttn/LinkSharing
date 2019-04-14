package com.ttn.LinkSharing.repositories;

import com.ttn.LinkSharing.entities.Resource;
import com.ttn.LinkSharing.entities.ResourceType;
import com.ttn.LinkSharing.entities.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Integer> {
    List<Resource> findAllByTopicIn(List<Topic> topics);

    Resource findByResourceId(Integer id);

    Integer deleteResourceByTopic(Topic topic);

    List<Resource> findAllByTopic(Topic topic);

    List<Resource> findAllByDescriptionLike(String search);

    List<Resource> findAllByDescriptionLikeAndTopic(String value,Topic topic);

    List<Resource> findAllByResourceTypeAndTopic(ResourceType resourceType,Topic topic);

}
