package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    List<Url> findByShortUrl(String shortUrl);
    List<Url> findByOriginalUrl(String fullUrl);
    List<Url> findByOriginalUrlAndShortUrl(String fullUrl, String shortUrl);

    @Query(value = "SELECT * FROM urls WHERE id IN (SELECT DISTINCT url_id FROM user_url_ownership WHERE user_id = :userId)", nativeQuery = true)
    Page<Url> findByOwnedByUser(Integer userId, Pageable pageable);
}
