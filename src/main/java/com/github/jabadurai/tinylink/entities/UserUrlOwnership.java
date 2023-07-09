package com.github.jabadurai.tinylink.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "user_url_ownership")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UserUrlOwnership extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "url_id")
    @ToString.Exclude
    private Url url;

}