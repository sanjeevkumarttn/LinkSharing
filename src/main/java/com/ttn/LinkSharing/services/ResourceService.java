package com.ttn.LinkSharing.services;

import com.ttn.LinkSharing.entities.*;
import com.ttn.LinkSharing.repositories.ResourceRepository;
import com.ttn.LinkSharing.repositories.SubscriptionRepository;
import com.ttn.LinkSharing.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    TopicService topicService;
    @Autowired
    HttpSession session;

    public static final String UPLOAD_FILE = "/home/ttn/Desktop/LinkSharing/src/main/resources/static/downloadedResourceFiles/";

    public Resource saveLinkResources(String url, String desc, String topicName) {
        ResourceType resourceType;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Topic topic = topicRepository.findByTopicNameAndUser(topicName, user);
            Resource resource = new Resource();
            resource.setUser(user);
            resource.setUrlPath(url);
            resource.setDescription(desc);
            resource.setTopic(topic);
            resourceType = ResourceType.LINK;
            resource.setResourceType(resourceType);
            Resource resource1 = resourceRepository.save(resource);
            return resource1;
        } else {
            //session.setAttribute("msg","Please Login First");
            return null;
        }
    }


    public Resource saveDownloadResources(MultipartFile file, String desc, String topicName) throws IOException {
        User user = (User) session.getAttribute("user");
        Topic topic = topicRepository.findByTopicNameAndUser(topicName, user);
        Resource resource = new Resource();
        if (user != null) {
            System.out.println(file.getName());
            byte[] bytes = file.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(UPLOAD_FILE + user.getUserName() + "_" + file.getOriginalFilename()));
            bout.write(bytes);
            bout.flush();
            bout.close();
            resource.setUrlPath("downloadedResourceFiles/" + user.getUserName() + "_" + file.getOriginalFilename());
            resource.setUser(user);
            resource.setTopic(topic);
            resource.setDescription(desc);
            resource.setResourceType(ResourceType.DOCUMENT);
            Resource resource1 = resourceRepository.save(resource);
            return resource1;
        } else {

            return null;
        }

    }


  // for post page
/*
    public List<Resource> getAllUserResources() {
        List<Resource> resourceList = resourceRepository.findAllByTopicIn(topicRepository.findAllByVisibility(Visibility.PUBLIC));

        session.setAttribute("resourceList", resourceList);
        return resourceList;

    }*/

    public Resource getResourceById(Integer id){
        return resourceRepository.findByResourceId(id);
    }

    public void deleteResourcesByTopic(Topic topic){

        List<Resource> resourceList = resourceRepository.findAllByTopic(topic);
        if(!resourceList.isEmpty()) {
            Iterator<Resource> resourceIterator = resourceList.iterator();
            while (resourceIterator.hasNext()) {
                Resource resource = resourceIterator.next();
                if (resource.getResourceType().name() == "DOCUMENT") {
                    File fileToDelete = new File("/home/ttn/linksharing/src/main/resources/static/" + resource.getUrlPath());
                    fileToDelete.delete();
                }
            }
            resourceRepository.deleteResourceByTopic(topic);
        }
    }

    public List<Resource> getResourceByTopic(Topic topic){
        return resourceRepository.findAllByTopic(topic);
    }

    public List<Resource> getResourcesByTopic(Topic topic) {
        List<Resource> resources = resourceRepository.findAllByTopic(topic);
        return resources;
    }


    public List<Resource> getResourcesOfsubscribedTopics(User user) {
        List<Topic> topicsOfUser = subscriptionService.getAllTopicsByUser(user);
        List<Resource> resourcesSubscribed = resourceRepository.findAllByTopicIn(topicsOfUser);
        return resourcesSubscribed;
    }

    public Resource updateResourceDescription(Integer resourceId, String description) {
        Optional<Resource> resourceOptional = resourceRepository.findById(resourceId);
        Resource resource = resourceOptional.orElse(null);
        if (resource == null)
            return null;
        resource.setDescription(description);

        return resourceRepository.save(resource);

    }

    public List<Resource> getAllResourcesByResourceSearch(String resourceName) {
        List<Resource> resourceDescList = resourceRepository.findAllByDescriptionLike('%' + resourceName + '%');
        List<Resource> resourceDescPublicList = new ArrayList<>();

        Iterator<Resource> resourceDescListResourceIterator = resourceDescList.iterator();
        while (resourceDescListResourceIterator.hasNext()){
            Resource resource = resourceDescListResourceIterator.next();
            if(resource.getTopic().getVisibility()== Visibility.PUBLIC){
                resourceDescPublicList.add(resource);
            }
        }

        List<Topic> topicList = topicService.getPublicTopicsByTopicNameSearch(resourceName);
        List<Resource> resourceTopicList = resourceRepository.findAllByTopicIn(topicList);

        List<Resource> finalResourceList = new ArrayList<>();
        finalResourceList.addAll(resourceDescPublicList);
        finalResourceList.addAll(resourceTopicList);

        return finalResourceList;
    }

    public List<Resource> getAllResourcesByDescAndTopicSearch(String value,Topic topic){
        return resourceRepository.findAllByDescriptionLikeAndTopic('%'+value+'%',topic);
    }

    public List<Resource> getAllDownloadableResourceTopicSpecific(Topic topic){
        ResourceType resourceType = ResourceType.DOCUMENT;
        return resourceRepository.findAllByResourceTypeAndTopic(resourceType,topic);
    }

    public List<Resource> getAllLinkResourceTopicSpecific(Topic topic){
        ResourceType resourceType = ResourceType.LINK;
        return resourceRepository.findAllByResourceTypeAndTopic(resourceType,topic);
    }
}
