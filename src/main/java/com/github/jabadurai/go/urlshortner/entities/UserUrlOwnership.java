package com.github.jabadurai.go.urlshortner.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_url_ownership")
@Data
public class UserUrlOwnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private Url url;

}