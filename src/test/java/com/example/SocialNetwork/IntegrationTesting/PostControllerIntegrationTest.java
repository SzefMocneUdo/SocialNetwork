package com.example.SocialNetwork.IntegrationTesting;
import com.example.SocialNetwork.post.Post;
import com.example.SocialNetwork.post.PostController;
import com.example.SocialNetwork.post.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    public void getAllPosts_ReturnsListOfPosts() throws Exception {
        Post post = new Post(1, "Example Post", "Content", "darek@example.com", 12, 4);
        when(postService.getAllPosts()).thenReturn(Collections.singletonList(post));

        mockMvc.perform(get("/post/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getValue()));
    }

    @Test
    public void getPostById_ReturnsPost() throws Exception {
        int postId = 1;
        Post post = new Post(1, "Example Post", "Content", "darek@example.com", 12, 4);

        // Wykonanie żądania GET do endpointu /post/{id}
        mockMvc.perform(get("/post/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getValue()));

        // Symulacja zachowania postService przy wywołaniu getPostById
        when(postService.getPostById(postId)).thenReturn(post);
    }


    @Test
    public void addPost_ReturnsAddedPost() throws Exception {
        Post post = new Post(1, "Example Post", "Content", "darek@example.com", 12, 4);
        when(postService.addPost(any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/post/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getValue()));
    }

    @Test
    public void updatePost_ReturnsUpdatedPost() throws Exception {
        int postId = 1;
        Post post = new Post(postId, "Example Post", "Content", "darek@example.com", 12, 4);
        when(postService.updatePost(any(Integer.class), any(Post.class))).thenReturn(post);

        mockMvc.perform(put("/post/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getValue()));
    }

    @Test
    public void likePost_ReturnsLikedPost() throws Exception {
        int postId = 1;
        Post post = new Post(postId, "Example Post", "Content", "darek@example.com", 12, 4);
        when(postService.likePost(postId)).thenReturn(post);

        mockMvc.perform(put("/post/{id}/like", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getValue()));
    }

    @Test
    public void deletePost_ReturnsNoContent() throws Exception {
        int postId = 1;
        doNothing().when(postService).deletePost(postId);

        mockMvc.perform(delete("/post/{id}", postId))
                .andExpect(status().isNoContent());
    }
}
