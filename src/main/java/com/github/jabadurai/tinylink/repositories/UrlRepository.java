package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Integer> {
    List<Url> findByShortUrl(String shortUrl);
    List<Url> findByOriginalUrl(String fullUrl);
    List<Url> findByOriginalUrlAndShortUrl(String fullUrl, String shortUrl);
}
