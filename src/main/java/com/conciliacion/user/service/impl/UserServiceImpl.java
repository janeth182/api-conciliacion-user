package com.conciliacion.user.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conciliacion.user.repository.UserRepository;
import com.conciliacion.user.service.UserService;
import com.conciliacion.user.entity.User;

@Service
public class UserServiceImpl implements UserService  {
	@Autowired
	private UserRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public User create(User user) {
		try {			
			return repository.save(user);	
		} catch (Exception e) {
			return null;
		}		
	}

	@Override
	@Transactional
	public User update(User user) {
		try {
			User registro = repository.findById(user.getIdUser()).orElseThrow();
			registro.setFirstName(user.getFirstName());
			registro.setLastName(user.getLastName());
			registro.setIdProfile(user.getIdProfile());
			registro.setIdDocumentType(user.getIdDocumentType());
			registro.setDocumentNumber(user.getDocumentNumber());
			registro.setAddress(user.getAddress());
			registro.setEmail(user.getEmail());
			return repository.save(registro);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			User registro = repository.findById(id).orElseThrow();
			repository.delete(registro);
		} catch (Exception e) {

		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByUserName(String userName, Pageable page) {
		try {
			return repository.findByUserNameContaining(userName,page);
		} catch (Exception e) {
			return null;
		}
	}
}
