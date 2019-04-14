package com.ttn.LinkSharing.services;

import com.ttn.LinkSharing.entities.User;
import com.ttn.LinkSharing.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class, value = "transactionManager") //for update pwd
public class UserService {

    @Autowired
    HttpSession session;
    @Autowired
    UserRepository userRepository;
    private static String UPLOADED_FOLDER = "/home/ttn/Desktop/LinkSharing/src/main/resources/static/userProfile/";

    public User addUser(User user, MultipartFile file) {
        user.setActive(true);
        user.setAdmin(false);
        user.setUserCreated(new Date());
        user.setUserUpdated(new Date());
        if (file.isEmpty())
            user.setPhoto("userProfile/default_image.png");
        else {
            try {

                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + user.getUserName() + "_" + file.getOriginalFilename());
                Files.write(path, bytes);
                user.setPhoto("userProfile/" + user.getUserName() + "_" + file.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userRepository.save(user);
    }

    public boolean doesEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() ? true : false;
    }

    public boolean doesUserNameExist(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.isPresent() ? true : false;
    }


    public User isValidUser(String userName, String password) {
        Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
        User user1 = user.isPresent() ? user.get() : null;
        return user1;
    }

    public Boolean findPasswordByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() ? true : false;
    }

    public int resetPassword(String password, String email) {
        System.out.println(password + " " + email);
        return userRepository.updatePassword(password, email);
    }

    //edit profile
    public int updateUser(User user, MultipartFile file, String previousUserName) {

        user.setUserUpdated(new Date());

        if (!file.isEmpty()) {
           // System.out.println("in if");
            try {
                System.out.println(user.getPhoto());
                File profileToDelete = new File("/home/ttn/Desktop/LinkSharing/src/main/resources/static/"+user.getPhoto());
                profileToDelete.delete();
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + user.getUserName() + "_" + file.getOriginalFilename());
                Files.write(path, bytes);
                user.setPhoto("userProfile/" + user.getUserName() + "_" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //System.out.println("in else");

            if(previousUserName != null) {
                //User user1 = (User)session.getAttribute("user");
                try {
                    String filePath = user.getPhoto();
                    System.out.println(user.getPhoto());
                    String oldProfileName = filePath;
                    String newProfileName = filePath.replace(previousUserName,user.getUserName());

                    System.out.println(">>>>>>"+oldProfileName);
                    System.out.println(">>>>>>>>>"+newProfileName);
                    File oldProfile = new File("/home/ttn/Desktop/LinkSharing/src/main/resources/static/"+oldProfileName);
                    //String profileToRename = oldProfile;
                    File newProfile = new File("/home/ttn/Desktop/LinkSharing/src/main/resources/static/"+newProfileName);
                    oldProfile.renameTo(newProfile);
                    user.setPhoto(newProfileName);
                    //System.out.println(oldProfile);
                } catch (Exception e) {

                }
            }
        }

        userRepository.save(user);

        Optional<User> user1 = userRepository.findByEmail(user.getEmail());
        User userCheck = user1.isPresent()?user1.get():null;
        if (userCheck.getFirstName().equals(user.getFirstName())&&userCheck.getLastName().equals(user.getLastName())
                &&userCheck.getUserName().equals(user.getUserName())&&userCheck.getPhoto().equals(user.getPhoto())) {
            session.setAttribute("user",user);
            return 1;
        } else if(userCheck==null)
            return 0;
        else
            return 0;
    }

    public Boolean doesUsernameExistsExceptThis(String uname) {
        Optional<User> user = userRepository.findByUserName(uname);
        System.out.println(" dlksnvkl : "+uname);
        if(!user.isPresent())
            return false;
        else
        {
            User sessionUser = (User)session.getAttribute("user");
            if(sessionUser.getUserId()==user.get().getUserId())
                return false;
        }
        return true;
    }


    //Admin
    public void updateUserActive(Boolean isActive, Integer id){
        userRepository.updateUserActive(isActive, id);
    }

    //email
    public Boolean findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return  user.isPresent()?true:false;
    }

    //invite
    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByMail(String mail){
        Optional<User> user = userRepository.findByEmail(mail);
        return user.orElse(null);
    }
}

