package de.emp2020.alertManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.emp2020.alertEditor.Alert;
import de.emp2020.users.User;


@CrossOrigin
@RestController
@RequestMapping("/app")
public class AlertManagerController {
	
	
	@Autowired
	AlertManagerService service;
	
	
	@GetMapping("/alerts")
	public Iterable<Alert> getAlertsByUserID (
			@RequestParam(name="userID", required=true) int userID,
			@RequestParam(name="userToken", required=false) String userToken)
	{
		
		// TODO: digest params for user authentification
		
		return service.getAlertsByUser(userID);
	}
	
	
	@PostMapping("/accept")
	public void acceptAlert (
			@RequestParam(name="user", required=true) int userID,
			@RequestParam(name="userToken", required=false) String userToken,
			@RequestParam(name="alertId", required=true) int alertID,
			@RequestParam(name="message", required=false) String message)
	{
		
		// TODO: digest params for user authentification
		
		service.acceptAlert(userID, alertID, message);
	}
	
	
	@PostMapping("/transfer")
	public void transferAlert (
			@RequestParam(name="user", required=false) int requestingUserID,
			@RequestParam(name="userToken", required=false) String userToken,
			@RequestParam(name="alertId", required=true) int alertID,
			@RequestParam(name="receiver", required=true) String targetUserName,
			@RequestParam(name="message", required=false) String message)
	{
		
		// TODO: digest params for user authentification
		
		service.transferAlert(alertID, targetUserName, message);
	}
	
	
	private static class Phone {
		
		private String user;
		private String password;
		private String userToken;
		
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getUserToken() {
			return userToken;
		}
		public void setUserToken(String userToken) {
			this.userToken = userToken;
		}
	}
	
	
	@PostMapping("/register")
	public Iterable<Alert> registerUser (@RequestBody Phone phone)
	{
		return service.registerUser(phone.user, phone.userToken, phone.password);
	}
	
	
	@PostMapping("/unregister")
	public boolean unregisterUser (
			@RequestParam(name="userToken", required=true) String userToken)
	{
		return service.unregisterUser(userToken);
	}
	
	
	@RequestMapping("/test")
	public String test ()
	{
		return "Test successfull";
	}
	
}
