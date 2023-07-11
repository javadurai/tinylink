package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.isActive = false where u.id = ?1")
    void deactivateUser(Integer userid);

    @Modifying
    @Query("update User u set u.isActive = true where u.id = ?1")
    void activateUser(Integer userid);

    @Modifying
    @Query("update User u set u.password = ?2 where u.id = ?1")
    void changePassword(Integer userid, String password);

    @Modifying
    @Query("update User u set u.email = ?1 where u.id = ?2")
    int updateEmail(String email, Integer userid);

    @Query(value = "SELECT count(1) FROM users WHERE is_active = TRUE", nativeQuery = true)
    int numberOfActiveUsers();

    @Query(value = "SELECT count(1) FROM users", nativeQuery = true)
    int totalUsers();

}
