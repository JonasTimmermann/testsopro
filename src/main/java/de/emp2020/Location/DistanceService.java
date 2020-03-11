package de.emp2020.Location;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.emp2020.alertEditor.Alert;
import de.emp2020.alertEditor.AlertEditorService;
import de.emp2020.users.UserToken;
import de.emp2020.users.UserTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class DistanceService {
    
    
	@Autowired
    DistanceRepository distanceRepo;
    
      
	@Autowired
    AlertEditorService alertService;
    
    @Autowired
    UserTokenService userTokenService;
    


    Logger log = LoggerFactory.getLogger(String.class);
    


    
    public DistanceService(DistanceRepository repo, AlertEditorService alertService, UserTokenService userTokenService){
        this.distanceRepo = repo;
        this.alertService = alertService;
        this.userTokenService = userTokenService;
	}



    // Add a new Place to the Repository
    public String saveDistance(DistanceObj distObj){

        List<Distance> dList = new ArrayList<>();
        distanceRepo.findAll().forEach(dList::add);

        Alert alert = alertService.getAlertByID(distObj.getAlertId());
        UserToken token = userTokenService.getUserTokenByToken(distObj.getUserToken());


        
        for(int i = 0; i < dList.size(); i++){
            
            if(dList.get(i).getAlert().getId() == distObj.getAlertId() && dList.get(i).getUserToken().getToken().equals(distObj.getUserToken())){
                Distance d = dList.get(i);
                d.setDistance(distObj.getDistance());
                distanceRepo.save(d);
                return "Changed";
            }
        }
      
       
        
        Distance dist = new Distance(distObj.getDistance(), alert, token);  //distObj.getId(),

        dist.setAlert(alert);
        dist.setUserToken(token);

        distanceRepo.save(dist);

    return "Distance saved";
    }



    public List<Distance> getAllDistance(){
        
        List<Distance> listDistance = new ArrayList<>();
		distanceRepo.findAll().forEach(listDistance::add);

        return listDistance;
    }

}