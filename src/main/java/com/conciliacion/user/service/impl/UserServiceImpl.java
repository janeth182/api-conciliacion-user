package com.conciliacion.user.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conciliacion.user.repository.UserRepository;
import com.conciliacion.user.service.CogniteService;
import com.conciliacion.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

import com.conciliacion.user.entity.User;
import com.conciliacion.user.dto.UserSignUpDTO;
@Service
@Slf4j
public class UserServiceImpl implements UserService  {
	@Autowired
	private UserRepository repository;
	@Autowired
	private CogniteService cognitoUserService;
	
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
			UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
			userSignUpDTO.setEmail(user.getEmail());
			userSignUpDTO.setLastname(user.getLastName());
			userSignUpDTO.setName(user.getFirstName());
			userSignUpDTO.setPassword("1234567890S0p0rt3*");
			
			String userSub = cognitoUserService.signUp(userSignUpDTO);
			log.info(userSub);
			user.setIdUserSub(userSub);
			return repository.save(user);	
		} catch (Exception e) {
			log.info(e.getMessage());
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
