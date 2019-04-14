package com.ttn.LinkSharing.entities;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @NotEmpty(message = "firstname is empty")
    private String firstName;

    @NotEmpty(message = "lastname is empty")
    private String lastName;

    @NotEmpty(message = "email is empty")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "username is empty")
    @Column(unique = true)
    private String userName;

    @Transient
    private String name = firstName + " " + lastName;

    private String password;

    @Transient
    private String confirmPassword;

    private String photo;

    @Temporal(value = TemporalType.DATE)
    private Date userCreated;

    @Temporal(value = TemporalType.DATE)
    private Date userUpdated;

    private boolean isActive;

    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
    Collection<Topic> topic = new HashSet<>();

    @OneToMany(mappedBy = "user")
    Collection<Resource> resources = new HashSet<>();

    public User() {
    }

    public User(@NotEmpty(message = "firstname is empty") String firstName, @NotEmpty(message = "lastname is empty") String lastName, @NotEmpty(message = "email is empty") String email, @NotEmpty(message = "username is empty") String userName, String name, String password, String confirmPassword, String photo, Date userCreated, Date userUpdated, boolean isActive, boolean isAdmin, Collection<Topic> topic, Collection<Resource> resources) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.photo = photo;
        this.userCreated = userCreated;
        this.userUpdated = userUpdated;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
        this.topic = topic;
        this.resources = resources;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Collection<Topic> getTopic() {
        return topic;
    }

    public void setTopic(Collection<Topic> topic) {
        this.topic = topic;
    }

    public void setUserCreated(Date userCreated) {
        this.userCreated = userCreated;
    }

    public void setUserUpdated(Date userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Date getUserCreated() {
        return userCreated;
    }

    public Date getUserUpdated() {
        return userUpdated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Collection<Resource> getResources() {
        return resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }


}
