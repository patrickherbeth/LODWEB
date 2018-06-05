package model;

public class User {
	
	public User(int userid, String first, String second, String email, String password) {
		super();
		this.userid = userid;
		this.first = first;
		this.second = second;
		this.email = email;
		this.password = password;
	}
	
	public User(int userid, String first, String second, String email) {
		super();
		this.userid = userid;
		this.first = first;
		this.second = second;
		this.email = email;
	}	

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
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

	private int userid;
	private String first;
	private String second;
	private String email;
	private String password;


}
