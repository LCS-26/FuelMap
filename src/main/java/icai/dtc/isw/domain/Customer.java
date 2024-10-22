package icai.dtc.isw.domain;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String email;
	private String password;
	private String secret;
	
	public Customer() {
		this.setId(new String());
		this.setName(new String());
	}
	
	public Customer(String id, String name) {
		this.setName(name);
		this.setId(id);
	}

	public Customer(String id, String name, String email, String password) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
		this.setSecret(new Random().nextInt(1000000)+"");
	}

	public Customer(String id, String name, String email, String password, String secret) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setPassword(password);
		this.setSecret(secret);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
