package de.emp2020.alertManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.emp2020.alertEditor.Alert;
import de.emp2020.alertEditor.AlertRepo;
import de.emp2020.notificationSender.NotificationSender;
import de.emp2020.users.User;
import de.emp2020.users.UserRepo;
import de.emp2020.users.UserToken;

@Service
@Transactional
public class AlertManagerService {
	
	@Autowired
	AlertRepo alertRepo;		// the JPA CRUD repository of all alerts
	@Autowired
	UserRepo userRepo;			// the JPA CRUD repository of all users
	@Autowired
	NotificationSender notificationSender;		// the component tasked with pushing any notifications to the Firebase
	@Autowired
	PromSender promSender;		// the component tasked with querying Prometheus for any alarming metrics
	
	Logger log = LoggerFactory.getLogger(AlertManagerService.class);
	
	
	/**
	 * Find all alerts based on the {@code owner}'s unique identificator
	 * The owner is the person who created the alert
	 * @param userID
	 * 		the unique identifier of the alert's owner
	 * @return
	 * 		an iterable over all alerts owned by the specified user
	 */
	public Iterable<Alert> getAlertsByUser (int userID)
	{
		return alertRepo.findByUserId (userID);
	}
	
	
	/**
	 * Calling user will be registered as dealing with the alert and all users assigned or transfered to the alert will be notified
	 * The alert must be triggered and the user must be assigned or transfered to it to be able to accept the alert
	 * @param userID
	 * 		the unique identifier of the user dealing with the alert
	 * @param alertID
	 * 		the unique identifier of the alert being dealt with
	 * @param message
	 * 		an optional message by the user
	 */
	public void acceptAlert (int userID, int alertID, String message)
	{
		Alert alert = alertRepo.findById(alertID).get();
		User user = userRepo.findById(userID).get();
		if (alert.getAcceptedUser() == null && alert.isTriggered() && alert.getAllUsers().contains(user))
		{
			alert.setAcceptedUser(user);
			notificationSender.acceptAlert(alert, message, alert.getAllUsers());
		}
	}
	
	
	/**
	 * Calling user will be transfer the alert to another user who will be notified about the alert and receive all future messages regarding the alert up until the alert becomes inactive again
	 * The alert must be triggered and the caller must be assigned to the alert to be able to transfer the alert
	 * @param alertID
	 * 		the unique identifier of the alert being dealt with
	 * @param targetUserName
	 * 		the unique identifier of the user the alert will be transfered to
	 * @param message
	 * 		an optional message by the user
	 */
	public void transferAlert (int alertID, String targetUserName, String message)
	{
		Alert alert = alertRepo.findById(alertID).get();
		if (alert.isTriggered())
		{
			User user = userRepo.findByUserName(targetUserName);
			alert.addTransferredUser(user);
			notificationSender.transferAlert(alert, message, Collections.singleton(user));
		}
	}
	
	
	/**
	 * Will prompt the service to query all alerts from prometheus and notify every user subscribed to them, should they be newly triggered
	 */
	public void triggerAlertQuerry ()
	{
		alertRepo.findAll().forEach(alert -> checkAlert(alert));
	}
	
	
	/**
	 * Registers a mobile device to receive notification for a specified user
	 * @param userName
	 * 		The uniquely identifying name of the user who registers
	 * @param userToken
	 * 		a unique token passed by the app with which they will be identified in Firebase
	 * @return
	 * 		an iterable over all alerts already owned by that user
	 */
	public Iterable<Alert> registerUser (String userName, String userToken, String password)
	{
		User user = userRepo.findByUserName(userName);
		if(!user.getPassword().equals(password)){
			return null;
		}

		UserToken token = new UserToken();
		
		token.setToken(userToken);
		List<UserToken> tokenList = new ArrayList<>();
		tokenList.add(token);
		user.setTokens(tokenList);

		//user.addToken(userToken);

		userRepo.save(user);
		
		return alertRepo.findByUserId(user.getId());
	}
	
	
	/**
	 * Unregisters a mobile device from receiving notifications
	 * @param userToken
	 * 		the unique token of the device
	 * @return
	 * 		{@code true} if device was successfully unregistered
	 */
	public boolean unregisterUser (String userToken)
	{
		userRepo.deleteUserToken(userToken);
		
		return true; //TODO: implement
	}
	
	
	/**
	 * Sends an alert's query to Prometheus and sends said alert if it was freshly triggered or resolves the alert if it was triggered before but has become inactive
	 * @param alert
	 * 		the alert to query and notify about
	 */
	private void checkAlert (Alert alert)
	{
		boolean triggered = promSender.checkPromIsTriggered(alert.getQuery());
		if (triggered && !alert.isTriggered())
		{
			alert.trigger();
			alertRepo.save(alert);
			notificationSender.sendAlert(alert, alert.getAllUsers());
		}
		else if (!triggered && alert.isTriggered())
		{
			alert.setTriggered(false);
			alertRepo.save(alert);
			notificationSender.resolveAlert(alert, alert.getAllUsers());
			alert.setAcceptedUser(null);
			alert.setTransferredUsers(null);
			alertRepo.save(alert);
		}
	}
	
}
