package com.example.x34_getcontacts.bean;

public class Contact {

	private String name;
	private String phone;
	private String Email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Contact(String name, String phone, String email) {
		super();
		this.name = name;
		this.phone = phone;
		Email = email;
	}
	@Override
	public String toString() {
		return "Contact [name=" + name + ", phone=" + phone + ", Email="
				+ Email + "]";
	}
	public Contact() {
		super();
	}
	
	
}
