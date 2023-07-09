package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.Url;
import com.github.jabadurai.tinylink.entities.UserUrlOwnership;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserUrlOwnershipRepository extends JpaRepository<UserUrlOwnership, Integer> {

    List<UserUrlOwnership> findByUserId(Integer userId);

    List<UserUrlOwnership> findByUrlId(Integer userId);
}
