package com.example.SocialNetwork.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/all")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("{id}")
    public Post getPostById(@PathVariable Integer id){
        return postService.getPostById(id);
    }

    @PostMapping("/add")
    public Post addPost(@RequestBody Post post) {

        return postService.addPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Integer id, @RequestBody Post post) {

        return postService.updatePost(id, post);
    }

    @PutMapping("/{id}/like")
    public Post likePost(@PathVariable Integer id) {
        return postService.likePost(id);
    }

    @PutMapping("/{id}/dislike")
    public Post dislikePost(@PathVariable Integer id) {
        return postService.dislikePost(id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Integer id) {

        postService.deletePost(id);
    }
}
