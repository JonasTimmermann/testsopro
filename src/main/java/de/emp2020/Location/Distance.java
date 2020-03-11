package de.emp2020.Location;

import de.emp2020.alertEditor.*;
import de.emp2020.users.User;
import de.emp2020.users.UserToken;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Distance {

    @GeneratedValue
    @Id
    private Integer id;
    
	@NotNull
    private String distance;

    
    //@JsonIgnore
    @OneToOne(cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })     //  (cascade=CascadeType.ALL)
    private Alert alert;

       //@JsonIgnore
    @OneToOne(cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })     //  (cascade=CascadeType.ALL)
    private UserToken token;

	
	
	public Distance(){

	}

	public Distance(String distance, Alert alert, UserToken token){
		//this.id = id;
        this.distance = distance;
        this.alert = alert;
        this.token = token;
	}

    


	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}


	public String getDistance(){
		return this.distance;
	}
	public void setDistance(String distance){
		this.distance = distance;
	}

	public Alert getAlert(){
		return this.alert;
	}
	public void setAlert(Alert alert){
		this.alert = alert;
    }
    
    public UserToken getUserToken(){
		return this.token;
	}
	public void setUserToken(UserToken token){
		this.token = token;
	}
	
	
}
