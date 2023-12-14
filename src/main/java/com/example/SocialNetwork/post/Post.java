package com.example.SocialNetwork.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_post")
public class Post {
    @jakarta.persistence.Id
    @GeneratedValue
    private Integer Id;
    private String title;
    private String value;
    private String ownerEmail;
    private int likes;
    private int dislikes;
}