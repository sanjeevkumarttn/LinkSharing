package com.ttn.LinkSharing.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Embeddable
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer resourceId;

    @NotEmpty
    private String description;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    private String urlPath;

    @Enumerated(EnumType.STRING)
    ResourceType resourceType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", description='" + description + '\'' +
                ", topic=" + topic +
                ", user=" + user +
                ", urlPath='" + urlPath + '\'' +
                ", resourceType=" + resourceType +
                '}';
    }
}

