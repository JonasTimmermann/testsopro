package de.emp2020.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AlertManagerTest {
	
//	@LocalServerPort
//	private int port;
	
//	@Autowired
//	private TestRestTemplate restTemplate;
	
//	@Test
//	void alertManagerControllerIsReachable () throws Exception {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/app/test", String.class)).contains("Test successfull");
//	}
	
	@Test
	void gettingAllAlertsByUserID ()
	{
//		fail("Test not implemented yet");
		//TODO: when getting alerts by user id, do not leave any alerts behind
	}
	
	@Test
	void notGettingOtherUsersAlerts ()
	{
//		fail("Test not implemented yet");
		//TODO: do not get alerts of other users than specified by id
	}
	
	@Test
	void gettingAllTransferredAlertsByUserID ()
	{
//		fail("Test not implemented yet");
		//TODO when getting alerts by user id, do not forget any transferred alerts
	}
	
	@Test
	void addingAlertToUserMessagesUsers ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void editingAlertMessagesUsers ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void removingAlertFromUserMessagesUser ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void correctAlertBeingAccepted ()
	{
//		fail("Test not implemented yet");
		//TODO when accepting alert, it chooses the correct alert
	}
	
	@Test
	void triggeredAlertMessagesCorrectUsers ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void triggeredAlertDoesNotMessageWrongUsers ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void transferringAlertsMessagesCorrectUser ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void transferringAlertsDoesNotMessageWrongUser ()
	{
//		fail("Test not implemented yet");
	}
	
	@Test
	void acceptingAlertsMessagesSubscribers ()
	{
//		fail("Test not implemented yet");
		//TODO when an alert is accepted, all users of the alert will be messaged
	}
}
