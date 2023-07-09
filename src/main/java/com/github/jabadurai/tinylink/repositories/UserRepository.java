package com.github.jabadurai.tinylink.repositories;

import com.github.jabadurai.tinylink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.isActive = false where u.id = ?1")
    void deactivateUser(Integer userid);

    @Modifying
    @Query("update User u set u.isActive = true where u.id = ?1")
    void activateUser(Integer userid);

    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    void updatePassword(String password, Integer userid);

    @Modifying
    @Query("update User u set u.email = ?1 where u.id = ?2")
    int updateEmail(String email, Integer userid);
}
