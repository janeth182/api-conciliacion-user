package com.conciliacion.user.service;
import com.conciliacion.user.dto.UserSignUpDTO;

public interface CogniteService {
	String signUp(UserSignUpDTO signUpDTO);
	String confirmSignUp(String userName,String code);
	boolean verifyAccessToken(String token);
}