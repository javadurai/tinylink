package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.UserUrlOwnership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserUrlOwnershipRepository extends JpaRepository<UserUrlOwnership, Integer> {

    List<UserUrlOwnership> findByUserId(Integer userId);

    List<UserUrlOwnership> findByUrlId(Integer userId);
}
