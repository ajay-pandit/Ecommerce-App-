package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponce;
import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponce>> getAllUser(){
        List<UserResponce> allUser=userService.fetchAllUsers();
        if(allUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allUser);
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Created");
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponce> getUserById(@PathVariable Long id){
        return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User updatedUser){
        userService.updateUserById(id, updatedUser);
        return "user updated";
    }
}
