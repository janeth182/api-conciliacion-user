package com.conciliacion.user.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conciliacion.user.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
	List<User> findByUserNameContaining(String userName, Pageable page);
}
