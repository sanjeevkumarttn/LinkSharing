package com.ttn.LinkSharing.services;

import com.ttn.LinkSharing.entities.*;
import com.ttn.LinkSharing.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class SubscriptionService {

    @Autowired
    HttpSession session;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public void addCreatorSubscriber(Topic topic) {

        System.out.println("Entered subscription create");
        User user = (User) session.getAttribute("user");

        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUser(user);
        subscription.setCreatedAt(new Date());
        subscription.setSeriousness(Seriousness.VERY_SERIOUS);
        System.out.println("before save in subscription create");

        subscriptionRepository.save(subscription);
        System.out.println("exit subscription create");

    }

    public Integer getSubscriptionCountOfUser(User user) {
        Integer topicSubscribed = subscriptionRepository.countAllByUser(user);
        return topicSubscribed;
    }



    public void addSubscriptionDetails(Topic topic,User user,String seriousness){
        Subscription subscription = new Subscription();
        Seriousness seriousness1 = null;
        if(seriousness.equals("SERIOUS"))
            seriousness1=Seriousness.SERIOUS;
        else if(seriousness.equals("CASUAL"))
            seriousness1=Seriousness.CASUAL;
        else if(seriousness.equals("VERY_SERIOUS"))
            seriousness1=Seriousness.VERY_SERIOUS;

        subscription.setUser(user);
        subscription.setTopic(topic);
        subscription.setSeriousness(seriousness1);
        subscription.setCreatedAt(new Date());

        subscriptionRepository.save(subscription);
    }

    public void removeSubscriptionDetails(Topic topic, User user) {
        Subscription subscription = subscriptionRepository.findByTopicAndUser(topic,user);
        subscriptionRepository.delete(subscription);
    }


    public void changeSeriousness(Topic topic, User user, String seriousness) {
        Seriousness seriousness1 = null;
        if(seriousness.equals("SERIOUS"))
            seriousness1=Seriousness.SERIOUS;
        else if(seriousness.equals("CASUAL"))
            seriousness1=Seriousness.CASUAL;
        else if(seriousness.equals("VERY_SERIOUS"))
            seriousness1=Seriousness.VERY_SERIOUS;

        Subscription subscription = subscriptionRepository.findByTopicAndUser(topic,user);
        subscription.setSeriousness(seriousness1);
        subscriptionRepository.save(subscription);
    }

/*    public void changeVisibility(Topic topic, User user, String visibility) {
        Visibility visibility1 = null;
        if(visibility.equals("PUBLIC"))
            visibility1=Visibility.PUBLIC;
        else if(visibility.equals("PRIVATE"))
            visibility1=Visibility.PRIVATE;

        *//*Subscription subscription = subscriptionRepository.findByTopicAndUser(topic,user);
        subscription.setSeriousness(seriousness1);
        subscriptionRepository.save(subscription);*//*
    }*/

    public List<Subscription> getAllSubscription(){
        return subscriptionRepository.findAll();
    }

    @Transactional
    public void deleteSubscribersByTopic(Topic topic){
        subscriptionRepository.deleteAllByTopic(topic);
    }


    public List<User> getAllUsersByTopic(Topic topic){
        List<Subscription> subscriptionList = subscriptionRepository.findAllByTopic(topic);
        List<User> usersList = new ArrayList<>();
        Iterator<Subscription> subscriptionIterator =  subscriptionList.iterator();
        while (subscriptionIterator.hasNext()){
            usersList.add(subscriptionIterator.next().getUser());
        }
        return usersList;
    }

    public HashMap<Integer,Integer> getSubscriptionCountOfUsersList(List<User> user){

        HashMap<Integer,Integer> subscriptionCountOfUsersList = new HashMap<>();
        Iterator<User> userIterator = user.iterator();
        while (userIterator.hasNext()){
            User user1 = userIterator.next();
            Integer topicSubscribed = subscriptionRepository.countAllByUser(user1);
            if(topicSubscribed==null)
                topicSubscribed=0;
            subscriptionCountOfUsersList.put(user1.getUserId(),topicSubscribed);
        }
        return subscriptionCountOfUsersList;
    }

    public ArrayList<Subscription> getAllSubscriptionDetailsByTopic(Topic topic){
        ArrayList<Subscription> subscriptionList = subscriptionRepository.findAllByTopic(topic);
        return subscriptionList;
    }

    public List<Topic> getAllTopicsByUser(User user){
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUser(user);
        List<Topic> topicsList = new ArrayList<>();
        Iterator<Subscription> subscriptionIterator =  subscriptionList.iterator();
        while (subscriptionIterator.hasNext()){
            topicsList.add(subscriptionIterator.next().getTopic());
        }
        return topicsList;
    }
}
