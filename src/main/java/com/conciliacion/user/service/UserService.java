package com.conciliacion.user.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.conciliacion.user.entity.User;

public interface UserService {
	public List<User> findAll(Pageable page);
	public List<User> findByUserName(String nombre, Pageable page);
	public User create(User user);
	public User update(User user);
	public void delete(int id);
}
