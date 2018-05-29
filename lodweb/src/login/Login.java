package login;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "login")
@ViewScoped
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login() {

	}
	
	private String login;
	
	private String sigup;

	public String getSigup() {
		
		System.out.println(this.getLogin());
		System.out.println(login);
		return sigup;
	}

	public void setSigup(String sigup) {
		this.sigup = sigup;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 

	

}
