package com.github.jabadurai.tinylink.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "urls")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Url extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Short URL cannot be empty")
    @Size(max = 255, message = "Short URL cannot exceed 255 characters")
    @Column(name = "short_url", unique = true, nullable = false)
    private String shortUrl;

    @NotBlank(message = "Original URL cannot be empty")
    @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "click_count")
    private Integer clickCount;

    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserUrlOwnership> userUrlOwnerships;

    public String getOwners(){
        List<String> owners = this.userUrlOwnerships.stream().map(o -> o.getUser().getFullName()).collect(Collectors.toList());
        return String.join(", ", owners);
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        // We check for null first to respect explicitly set values
        if (this.clickCount == null) {
            this.clickCount = 0;
        }
    }
}
