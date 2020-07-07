package com.github.jabadurai.go.urlshortner.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class UrlData extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{full_url.not_null}")
    @Size(min = 4, message = "{full_url.size.limit}")
    private String fullUrl;

    @NotNull(message = "{short_url.not_null}")
    @Size(min = 4, max = 50, message = "{short_url.size.limit}")
    private String shortUrl;

    public UrlData(){

    }

    public UrlData(String fullUrl, String shortUrl) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString() {
        return "UrlData{" +
                "id=" + id +
                ", fullUrl='" + fullUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}
