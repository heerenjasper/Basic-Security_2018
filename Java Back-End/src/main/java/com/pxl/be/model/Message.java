package com.pxl.be.model;

import java.io.File;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="USER_MESSAGE")
public class Message implements Serializable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Column(name="text", nullable=false)
	private String text;
	
	@NotEmpty
	@Column(name="EncryptedFile_path", nullable=true)
	private String EncryptedFilePath;
	
	@NotEmpty
	@Column(name="DecryptedFile_path", nullable=true)
	private String DecryptedFilePath;
	
	public Message(String text, File file, String publicKeyPath, String privateKeyPath) {
		super();
		this.id = id;
		this.text = text;
		this.DecryptedFilePath = publicKeyPath;
		this.DecryptedFilePath = privateKeyPath;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEncryptedFilePath() {
		return EncryptedFilePath;
	}

	public void setEncryptedFilePath(String encryptedFilePath) {
		EncryptedFilePath = encryptedFilePath;
	}

	public String getDecryptedFilePath() {
		return DecryptedFilePath;
	}

	public void setDecryptedFilePath(String decryptedFilePath) {
		DecryptedFilePath = decryptedFilePath;
	}
	
}
