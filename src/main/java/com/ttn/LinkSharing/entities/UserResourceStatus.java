package com.ttn.LinkSharing.entities;

import javax.persistence.*;

@Entity
public class UserResourceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer statusId;

    @ManyToOne
    private Resource resourceId;

    @ManyToOne
    private User userId;

    private String status;

    public UserResourceStatus() {
    }

    public UserResourceStatus(Resource resourceId, User userId, String status) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.status = status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Resource getResourceId() {
        return resourceId;
    }

    public void setResourceId(Resource resourceId) {
        this.resourceId = resourceId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
