package com.poly.main.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "Users")
public class User implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "{NotEmpty.User.Fullname}")
	private String fullname;
	@NotEmpty(message = "{NotEmpty.User.Username}")
	private String username;
	@NotEmpty(message = "{NotEmpty.User.Password}")
	@Size(min = 6,message = "{Size.User.Password}")
	private String password;
	@NotEmpty(message = "{NotEmpty.User.email}")
	@Email(message = "{Email.User.email}")
	private String email;
	@NotNull(message = "{NotNull.User.admin}")
	private boolean admin;
}
