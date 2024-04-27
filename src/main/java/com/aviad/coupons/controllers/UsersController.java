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

    @PostMapping
    public void createUser(@RequestBody User user) throws ApplicationException {
        this.userLogic.addUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.userLogic.updateUser(user, token);
    }

    @GetMapping
    public List<User> getUsers (@RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getAllUsers(token);
    }

    @PostMapping("/admin")
    public void createUserAdmin(@RequestBody User user, @RequestHeader("Authorization") String token) throws ApplicationException {
        this.userLogic.addUserAdmin(user,token);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) throws ApplicationException {
        return this.userLogic.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) throws ApplicationException {
        this.userLogic.deleteUser(id);
    }

    @GetMapping("/byToken")
    public User getUserByToken(@RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getUserByToken(token);
    }


    @GetMapping("/byCompanyId")
    public List<User> getUsersByCompanyId (@RequestParam("id") int id, @RequestHeader("Authorization") String token) throws ApplicationException {
        return this.userLogic.getUsersByCompanyId(id, token);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws Exception {
        return this.userLogic.login(userLoginData);
    }

}
