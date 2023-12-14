package com.example.SocialNetwork.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(Integer id){
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(Integer id, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setValue(updatedPost.getValue());
            post.setLikes(updatedPost.getLikes());
            post.setDislikes(updatedPost.getDislikes());
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }

    public Post likePost(Integer id) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setLikes(post.getLikes() + 1);
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }

    public Post dislikePost(Integer id) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setDislikes(post.getDislikes() + 1);
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }
}
