package com.poly.main.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
	int id;
	@NotEmpty(message = "{NotEmpty.User.Fullname}")
	String fullname;
	@NotEmpty(message = "{NotEmpty.User.Username}")
	String username;
	@NotEmpty(message = "{NotEmpty.User.Password}")
	@Size(min = 6,message = "{Size.User.Password}")
	String password;
	@NotEmpty(message = "{NotEmpty.User.email}")
	@Email(message = "{Email.User.email}")
	String email;
	@NotNull(message = "{NotNull.User.admin}")
	boolean admin;
	boolean remember;
}
