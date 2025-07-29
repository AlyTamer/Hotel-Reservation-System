package com.aly.brightskies.task3.repositories;

import com.aly.brightskies.task3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
     User findByUserName(String username);

     boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
