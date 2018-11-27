package com.adrian.blog.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * The User Entity class
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018.
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	@NotNull
	private String username;

	@Column(name = "password")
	@NotNull
	private String password;

	@Transient
	private String password_2;

	@Column(name = "email", unique = true)
	@NotNull
	private String email;

	private String provincia;

	private int telefono;

	@Column(name = "role")
	@NotNull
	private int role;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	@Column(name = "last_session")
	private Date lastSession;

	@PrePersist
	public void PrePersist() {
		createAt = new Date();
	}

	@Column(name = "reset_token")
	private String resetToken;

	public User() {
	}

	public User(String username, String password, String email, int role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setRole(role);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPassword_2() {
		return password_2;
	}

	public void setPassword_2(String password_2) {
		this.password_2 = password_2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLastSession() {
		return lastSession;
	}

	public void setLastSession(Date lastSession) {
		this.lastSession = lastSession;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	/**
	 * @Override public boolean equals(Object o) { if (this == o) return true; if (o
	 *           == null || getClass() != o.getClass()) return false; User user =
	 *           (User) o; return id == user.id && role == user.role &&
	 *           Objects.equals(username, user.username) && Objects.equals(password,
	 *           user.password) && Objects.equals(password_2, user.password_2) &&
	 *           Objects.equals(email, user.email); }
	 */

	@Override
	public int hashCode() {

		return Objects.hash(id, username, password, password_2, email, role);
	}

}
