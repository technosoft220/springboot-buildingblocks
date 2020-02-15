package com.technosoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technosoft.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String username);

}
