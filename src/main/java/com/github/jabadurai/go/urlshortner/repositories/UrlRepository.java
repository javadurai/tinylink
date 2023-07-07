package com.github.jabadurai.go.urlshortner.repositories;

import com.github.jabadurai.go.urlshortner.entities.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlRepository extends CrudRepository<Url, Integer> {
    List<Url> findByShortUrl(String shortUrl);
    List<Url> findByOriginalUrl(String fullUrl);
    List<Url> findByOriginalUrlAndShortUrl(String fullUrl, String shortUrl);
}
