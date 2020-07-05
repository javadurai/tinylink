package com.github.jabadurai.go.urlshortner.repositories;

import com.github.jabadurai.go.urlshortner.entities.UrlData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlDataRepository extends CrudRepository<UrlData, Long> {
    List<UrlData> findByShortUrl(String shortUrl);
    List<UrlData> findByFullUrl(String fullUrl);
    List<UrlData> findByFullUrlAndShortUrl(String fullUrl,String shortUrl);
}
