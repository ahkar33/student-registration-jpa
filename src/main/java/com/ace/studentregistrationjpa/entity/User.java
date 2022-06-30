package com.ace.studentregistrationjpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	@Id
	private String id;
	private String email;
	private String name;
	private String password;
	@Transient
	private String confirmPassword;
	private String userRole;

	public User(String id,String email, String name, String password, String userRole) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.userRole = userRole;
	}

}
