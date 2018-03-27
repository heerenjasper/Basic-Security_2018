package com.pxl.be.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="APP_USER")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@NotEmpty
	@Column(name="publickey_path", nullable=true)
	private String publicKeyId;

	@NotEmpty
	@Column(name="publickey_path", nullable=true)
	private String privateKeyId;
	
	public User(int id, String email, String publicKeyId, String privateKeyId) {
		super();
		this.id = id;
		this.email = email;
		this.publicKeyId = publicKeyId;
		this.privateKeyId = privateKeyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPublicKeyId() {
		return publicKeyId;
	}

	public void setPublicKeyId(String publicKeyId) {
		this.publicKeyId = publicKeyId;
	}

	public String getPrivateKeyId() {
		return privateKeyId;
	}

	public void setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
	}
		
}
