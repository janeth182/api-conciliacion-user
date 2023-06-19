package com.conciliacion.user.service.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;

import com.conciliacion.user.configuration.AwsConfig;
import com.conciliacion.user.dto.UserSignUpDTO;
import com.conciliacion.user.entity.UserCognito;
import com.conciliacion.user.service.CogniteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CogniteServiceImpl implements CogniteService {
	private final AWSCognitoIdentityProvider awsCognitoIdentityProvider;
	private final AwsConfig awsConfig;
	@Override
	public String signUp(UserSignUpDTO signUpDTO) {
		 try {
		      final SignUpRequest signUpRequest = new SignUpRequest()
	                  .withClientId(awsConfig.getCognito().getAppClientId())	                  
	                  .withPassword(signUpDTO.getPassword())
	                  .withUsername(signUpDTO.getEmail())
	                  .withUserAttributes(
	                          new AttributeType().withName("name").withValue(signUpDTO.getName()),
	                          new AttributeType().withName("family_name").withValue(signUpDTO.getLastname()),
	                          new AttributeType().withName("email").withValue(signUpDTO.getEmail()));
		      
		      SignUpResult createUserResult = awsCognitoIdentityProvider.signUp(signUpRequest);
	          log.info("Created User id: {}", createUserResult.getUserSub());
	          
	          return createUserResult.getUserSub().toString();
		      	
			 } catch (com.amazonaws.services.cognitoidp.model.UsernameExistsException e) {
		            throw new UsernameExistsException("User name that already exists");
		     }
	}
	
	@Override
	public String confirmSignUp(String userName,String code) {
		 try {
			 log.info("userName: {}", userName);
			 log.info("code: {}", code);
		      final ConfirmSignUpRequest signUpRequest = new ConfirmSignUpRequest()
	                  .withClientId(awsConfig.getCognito().getAppClientId())	                  
	                  .withConfirmationCode(code)
	                  .withUsername(userName);
		      
		      ConfirmSignUpResult result = awsCognitoIdentityProvider.confirmSignUp(signUpRequest);
	          log.info("Confirm User: {}", result.getSdkHttpMetadata().getHttpStatusCode());
	          
	          return "Registro confirmado exitosamente.";
		      	
			 } catch (CodeMismatchException e) {
				 log.info(e.getMessage());			       
				 return "El código de confirmación es incorrecto.";
			 } catch (ExpiredCodeException e) {
				 log.info(e.getMessage());   
				 return "El código de confirmación ha expirado.";
			 } catch (Exception e) {
				 log.info(e.getMessage());   
				 return "error";
			 }
	}

	@Override
	public boolean verifyAccessToken(String accessToken) {
 		
		 GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);				 
		  try {
		        GetUserResult getUserResult = awsCognitoIdentityProvider.getUser(getUserRequest);
		        log.info("Usuario verificado: " + getUserResult.getUsername());
		        return true;
		    } catch (NotAuthorizedException e) {
		    	log.info(e.getMessage());
		    	log.info("El accessToken no es válido.", e.getMessage());
		        return false;
		    } catch (Exception e) {
		    	log.info("Ocurrió un error al verificar el accessToken.", e.getMessage());
		        return false;
		    }
	}
	
}
