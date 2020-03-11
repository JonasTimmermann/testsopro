package de.emp2020.notificationSender;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.emp2020.alertEditor.IAlert;
import de.emp2020.users.IUser;

@Service
public class TempNotificationSender implements NotificationSender {
	
	Logger log = LoggerFactory.getLogger(TempNotificationSender.class);

	@Override
	public void registerAlert(IAlert alert, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Users: " + users);
	}

	@Override
	public void unregisterAlert(IAlert alert, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Users: " + users);
	}

	@Override
	public void sendAlert(IAlert alert, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Users: " + users);
		
	}

	@Override
	public void transferAlert(IAlert alert, String message, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Message: " + message + " | Users: " + users);
		
	}

	@Override
	public void acceptAlert(IAlert alert, String message, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Message: " + message + " | Users: " + users);
		
	}

	@Override
	public void resolveAlert(IAlert alert, Collection<IUser> users) {
		// TODO Auto-generated method stub
		log.info("Alert: " + alert + " | Users: " + users);
		
	}

}
