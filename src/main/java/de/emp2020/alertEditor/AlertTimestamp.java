package de.emp2020.alertEditor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AlertTimestamp {
	
	@Id
	@GeneratedValue
	private Long id;		// autogenerated ID for internet identification, not of importance to other users
	private Long stamp;		// unix timestamp
	
	
	// Autogenerated getters and setters
	
	public void setStamp (Long stamp)
	{
		this.stamp = stamp;
	}
	
	public Long getStamp ()
	{
		return stamp;
	}
	
}
