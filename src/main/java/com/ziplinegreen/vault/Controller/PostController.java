package com.ziplinegreen.vault.Controller;

import com.ziplinegreen.vault.Exception.ResourceNotFoundException;
import com.ziplinegreen.vault.Model.Post;
import com.ziplinegreen.vault.Repository.PostRepository;
import com.ziplinegreen.vault.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/posts")
    public Page<Post> getAllPostsByUserId(@PathVariable(value = "userId") Long userId,
                                             Pageable pageable) {
        return postRepository.findByUserId(userId, pageable);
    }

    @PostMapping("/users/{userId}/posts")
    public Post createPost(@PathVariable (value = "userId") Long userId,
                                 @Valid @RequestBody Post post) {
        return userRepository.findById(userId).map(user -> {
            post.setUser(user);
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }

    @PutMapping("/users/{userId}/posts/{postId}")
    public Post updateComment(@PathVariable (value = "userId") Long userId,
                                 @PathVariable (value = "postId") Long postId,
                                 @Valid @RequestBody Post postRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("UserId " + userId + " not found");
        }

        return postRepository.findById(postId).map(post -> {
            post.setMessage(postRequest.getMessage());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + "not found"));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "userId") Long userId,
                                           @PathVariable (value = "postId") Long postId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("UserId " + userId + " not found");
        }

        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
