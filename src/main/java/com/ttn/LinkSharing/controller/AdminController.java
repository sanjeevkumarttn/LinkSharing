package com.ttn.LinkSharing.controller;

import com.ttn.LinkSharing.repositories.UserRepository;
import com.ttn.LinkSharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/usersRecord")
    public String getUserRecord(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("users", userRepository.findAll(new PageRequest(page, 20)));
        model.addAttribute("currentPage", page);

        return "usersForAdmin";
    }

    @GetMapping("/activateUser/{id}")
    public String activateUser(@PathVariable Integer id) {
        userService.updateUserActive(true, id);
        return "redirect:/usersRecord";
    }

    @GetMapping("/deactivateUser/{id}")
    public String deactivateUser(@PathVariable Integer id) {
        userService.updateUserActive(false, id);
        return "redirect:/usersRecord";
    }

   /* @PostMapping("/showRecordsBySelected")
    public String getUsersBySelected(Model model, String value){
        //System.out.println(value);
        List<User> users = null;
        if(value.equals("true"))
             users = adminService.getUserByActive(true);
        else if(value.equals("false"))
            users = adminService.getUserByActive(false);
        else
            users = adminService.getAllUser();
        //model.addAttribute("selectValue",value);
        model.addAttribute("users",users);
        //System.out.println(users);
        return "usersForAdmin";
    }

    @PostMapping("/sortIdByAscendingOrder")
    public String getUserSortIdByAscendingOrder(Model model){
        List<User> users = adminService.getUsersSortedByIdAscending();
        model.addAttribute("users",users);
        return "usersForAdmin";
    }

    @PostMapping("/sortIdByDescendingOrder")
    public String getUserSortIdByDescendingOrder(Model model){
        List<User> users = adminService.getUsersSortedByIdDescending();

        model.addAttribute("users",users);
        return "usersForAdmin";
    }*/
}
