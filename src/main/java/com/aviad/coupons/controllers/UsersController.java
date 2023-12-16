package com.aviad.coupons.controllers;

import com.aviad.coupons.beans.UserLoginData;
import com.aviad.coupons.dto.User;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UserLogic userLogic;

    @Autowired
    public UsersController(UserLogic userLogic){
        this.userLogic = userLogic;
    }

    // Add user
    @PostMapping
    public void createUser(@RequestBody User user) throws ApplicationException {
        this.userLogic.addUser(user);
    }

    // Update user
    @PutMapping
    public void updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.userLogic.updateUser(user, token);
    }

    // Get all users
    @GetMapping
    public List<User> getUsers (@RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getAllUsers(token);
    }

    // Get user
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) throws ApplicationException {
        return this.userLogic.getUser(id);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) throws ApplicationException {
        this.userLogic.deleteUser(id);
    }

    // Get user by token
    @GetMapping("/byToken")
    public User getUserByToken(@RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getUserByToken(token);
    }


    // Get users by company id
    @GetMapping("/byCompanyId")
    public List<User> getUsersByCompanyId (@RequestParam("id") int id, @RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getUsersByCompanyId(id, token);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws Exception {
        return this.userLogic.login(userLoginData);
    }

}
