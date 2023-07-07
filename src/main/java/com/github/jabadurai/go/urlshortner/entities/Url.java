package com.github.jabadurai.go.urlshortner.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "urls")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Url extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_url", unique = true, nullable = false)
    private String shortUrl;

    @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "click_count", nullable = false)
    private Integer clickCount;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL)
    private Set<UserUrlOwnership> userUrlOwnerships;

    public String getOwners(){
        StringBuilder owners= new StringBuilder();
        for (UserUrlOwnership userUrlOwnership :  userUrlOwnerships) {
            owners.append(userUrlOwnership.getUser().getUsername() + " ");
        }

        return owners.toString();
    }
}
