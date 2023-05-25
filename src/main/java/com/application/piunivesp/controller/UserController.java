package com.application.piunivesp.controller;

import com.application.piunivesp.model.User;
import com.application.piunivesp.resources.AbstractCrudController;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/users")
public class UserController extends AbstractCrudController<User> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AbstractService getService() {
        return this.userService;
    }


    @PostMapping(value = "/reset-password")
    public ResponseEntity<Void> resetPassword(HttpServletRequest request,
                                              @RequestParam("email") String userEmail) {
        userService.resetPassword(request, userEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody User user,
                                               @RequestParam("token") String token) {
        userService.resetPassword(user, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value= "/create-user")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.ok().build();
    }
}
