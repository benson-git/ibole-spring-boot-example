package io.ibole.springboot.example.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	private String name;
    private String password;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public User setName(String name) {
		this.name = name;
		return this;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
