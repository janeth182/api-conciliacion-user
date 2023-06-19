package com.conciliacion.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class UserSignUpDTO {
	 @NotNull
	 @NotBlank
	 private String email;
	 
	 
	 private String password;
	 
	 @NotNull
	 @NotBlank
	 private String name;
	 
	 @NotNull
	 @NotBlank
	 private String lastname;
}
