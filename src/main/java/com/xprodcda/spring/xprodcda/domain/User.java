package com.xprodcda.spring.xprodcda.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="User")
public class User implements Serializable{
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="Id",nullable=false, updatable=false)
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private Long id; //id for the DB

@Column(name="UserId")
private String userId; //Id of the user

@Column(name="Firstname")
private String firstname;

@Column(name="Lastname")
private String lastname;

@Column(name="Username")
private String username;

@Column(name="Password")
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;

@Column(name="Email")
private String email;

@Column(name="Image")
private String profileImageUrl;

private Date lastLoginDate;
private Date lastLoginDateDisplay;
private Date joinDate;
private String role;  //ROLE_USER{read, edit} ROLE_ADMIN {delete}
private String[]authorities; //Are permissions: read, edit, delete
private boolean isActive;
private boolean isNotLocked;


public User() {
	super();
}


public User(Long id, String userId, String firstname, String lastname, String username, String password, String email,
		String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String role,
		String[] authorities, boolean isActive, boolean isNotLocked) {
	super();
	this.id = id;
	this.userId = userId;
	this.firstname = firstname;
	this.lastname = lastname;
	this.username = username;
	this.password = password;
	this.email = email;
	this.profileImageUrl = profileImageUrl;
	this.lastLoginDate = lastLoginDate;
	this.lastLoginDateDisplay = lastLoginDateDisplay;
	this.joinDate = joinDate;
	this.role = role;
	this.authorities = authorities;
	this.isActive = isActive;
	this.isNotLocked = isNotLocked;
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getUserId() {
	return userId;
}


public void setUserId(String userId) {
	this.userId = userId;
}


public String getFirstname() {
	return firstname;
}


public void setFirstname(String firstname) {
	this.firstname = firstname;
}


public String getLastname() {
	return lastname;
}


public void setLastname(String lastname) {
	this.lastname = lastname;
}


public String getUsername() {
	return username;
}


public void setUsername(String username) {
	this.username = username;
}


public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public String getProfileImageUrl() {
	return profileImageUrl;
}


public void setProfileImageUrl(String profileImageUrl) {
	this.profileImageUrl = profileImageUrl;
}


public Date getLastLoginDate() {
	return lastLoginDate;
}


public void setLastLoginDate(Date lastLoginDate) {
	this.lastLoginDate = lastLoginDate;
}


public Date getLastLoginDateDisplay() {
	return lastLoginDateDisplay;
}


public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
	this.lastLoginDateDisplay = lastLoginDateDisplay;
}


public Date getJoinDate() {
	return joinDate;
}


public void setJoinDate(Date joinDate) {
	this.joinDate = joinDate;
}


public String getRole() {
	return role;
}


public void setRole(String role) {
	this.role = role;
}


public String[] getAuthorities() {
	return authorities;
}


public void setAuthorities(String[] authorities) {
	this.authorities = authorities;
}


public boolean isActive() {
	return isActive;
}


public void setActive(boolean isActive) {
	this.isActive = isActive;
}


public boolean isNotLocked() {
	return isNotLocked;
}


public void setNotLocked(boolean isNotLocked) {
	this.isNotLocked = isNotLocked;
}



}
