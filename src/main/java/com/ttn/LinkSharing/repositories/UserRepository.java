package com.ttn.LinkSharing.repositories;

import com.ttn.LinkSharing.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameAndPassword(String userName, String password);

    @Modifying
    @Query("UPDATE User u set u.password = :password where u.email = :email")
    int updatePassword(@Param("password") String password,@Param("email") String email);


    //Admin

    Page<User> findAll(Pageable pageable);
    // List<User> findAll();

   /* List<User> findAllByIsActive(Boolean active);

    List<User> findAllByOrderByUserIdAsc();

    List<User> findAllByOrderByUserIdDesc();*/

    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.userId = :userId")
    int updateUserActive(@Param("isActive") Boolean isActive, @Param("userId") Integer userId);

}
