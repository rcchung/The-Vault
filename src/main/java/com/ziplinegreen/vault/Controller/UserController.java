package com.ziplinegreen.vault.Controller;

import com.ziplinegreen.vault.Exception.ResourceNotFoundException;
import com.ziplinegreen.vault.Model.User;
import com.ziplinegreen.vault.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getAllPosts(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createPost(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{userId}")
    public User updatePost(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(userRequest.getUsername());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }


    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deletePost(@PathVariable Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
}