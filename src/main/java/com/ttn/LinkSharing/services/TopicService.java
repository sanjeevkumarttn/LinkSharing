package com.ttn.LinkSharing.services;

import com.ttn.LinkSharing.entities.Topic;
import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.entities.Visibility;
import com.ttn.LinkSharing.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class TopicService {
    @Autowired
    ResourceService resourceService;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    HttpSession session;

    @Autowired
    RatingService ratingService;

    @Autowired
    SubscriptionService subscriptionService;

   public Topic addTopic(String topicname, String visibility) {

        System.out.println("Entered add topic of service");

        Topic topic = new Topic();

        Visibility visibility1;
        if (visibility.equals("Public")) {
            visibility1 = Visibility.PUBLIC;
        } else {
            visibility1 = Visibility.PRIVATE;
        }

        User user = (User) session.getAttribute("user");

        List<Integer> subscribers = new ArrayList<>();
        subscribers.add(user.getUserId());

        topic.setTopicName(topicname);
        topic.setUser(user);
        topic.setCreatedAt(new Date());
        topic.setUpdatedAt(new Date());
        topic.setVisibility(visibility1);
        topic.setSubscribers(subscribers);

        Topic topicCreated = topicRepository.save(topic);

        subscriptionService.addCreatorSubscriber(topicCreated);
        return topic;
    }

    public Boolean checkTopicNameUnique(String topicname){
        User user = (User)session.getAttribute("user");
        Topic topic = topicRepository.findByTopicNameAndUser(topicname,user);
        if(topic==null)
            return true;
        else
            return false;
    }

   public Integer getTopicsCreatedCountOfUser(User user){
        Integer topicsCreated = topicRepository.countAllByUser(user);
        return topicsCreated;
    }

    public List<Topic> getSubscribedTopicsByUSer(){
        User user = (User) session.getAttribute("user");
        Integer id = user.getUserId();
        List<Topic> topic = topicRepository.findBySubscribers(id);
        //System.out.println("topic size : "+topic.size());
        if(!topic.isEmpty())
            return topic;
        return null;
    }




   /* public Iterable<Topic> getAllTopics(){
        Iterable<Topic> topics = topicRepository.findAll();
        return  topics;
    }*/

    public Topic getTopicById(Integer id){
        Optional<Topic> topic = topicRepository.findById(id);
        //System.out.println("topic available: "+topic.isPresent());
        return topic.get();
    }

    public void addSubscriber(Topic topic,User user,String seriousness){
        List<Integer> subscriberList = topic.getSubscribers();
        subscriberList.add(user.getUserId());
        topicRepository.save(topic);
        subscriptionService.addSubscriptionDetails(topic,user,seriousness);
    }

    public void removeSubscriber(Topic topic,User user){
        List<Integer> subscriberList = topic.getSubscribers();
        subscriberList.remove(user.getUserId());
        topicRepository.save(topic);
        subscriptionService.removeSubscriptionDetails(topic,user);
    }

    public List<Topic> userTopics(){
       List<Topic> topics = (List)topicRepository.findByUser((User) session.getAttribute("user"));
        //System.out.println(topics);
       if(topics.isEmpty())
        System.out.println("null");
       else
           System.out.println(topics);
        //return topicRepository.findAll();
      return topics;
    }


    public String changeTopicName(Integer topicId,String newname){
        Optional<Topic> topic = topicRepository.findById(topicId);
        Topic topic1 = topic.orElse(null);
        if(topic1!=null){
            topic1.setTopicName(newname);
            topic1.setUpdatedAt(new Date());
            topicRepository.save(topic1);
            topic = topicRepository.findById(topicId);
            return topic.get().getTopicName();
        }
        else
            return null;
    }

    /*public List<Topic> allTopics(){
       List<Topic> topics = topicRepository.findAllByVisibility(Visibility.PUBLIC);
           return topics;
    }*/

    @Transactional
    public void deleteTopic(Topic topic){
        subscriptionService.deleteSubscribersByTopic(topic);
        ratingService.deleteRating(topic);
        resourceService.deleteResourcesByTopic(topic);
        topicRepository.delete(topic);
    }


    public void changeVisibility(Topic topic, String visibility) {
        System.out.println("in changeVisibility service: "+visibility);
        Visibility visibility1=topic.getVisibility();
        if(visibility.equals("PUBLIC")) {
            visibility1 = Visibility.PUBLIC;
        }
        else if(visibility.equals("PRIVATE")) {
            visibility1 = Visibility.PRIVATE;
        }


        topic.setVisibility(visibility1);
        topic.setUpdatedAt(new Date());

        topicRepository.save(topic);
    }


    public Iterable<Topic> getAllTopics(){
        return topicRepository.findAll();
    }


    public List<Topic> getPublicTopicsByTopicNameSearch(String topicname) {
        List<Topic> topics = topicRepository.findAllByVisibilityAndTopicNameLike(Visibility.PUBLIC,'%' + topicname + '%');
        return topics;
    }

    /*public List<Topic> getAllPrivateTopics() {
        Visibility visibility = Visibility.PRIVATE;
        return topicRepository.findAllByVisibilty(visibility);
    }*/

}
