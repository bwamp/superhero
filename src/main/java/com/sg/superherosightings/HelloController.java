package com.sg.superherosightings;

import com.sg.superherosightings.dto.Sighting;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sg.superherosightings.service.HeroService;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperpowerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

@Controller
public class HelloController {
        
    LocationService myLocation;
    HeroService myHero;
    OrganizationService myOrg;
    SuperpowerService myPower;
    SightingService mySight;
    
    @Inject
    public HelloController (LocationService myLocation, HeroService myHero, OrganizationService myOrg, SuperpowerService myPower, SightingService mySight){
        this.myLocation = myLocation;
        this.myHero= myHero;
        this.myOrg = myOrg;
        this.myPower = myPower;
        this.mySight = mySight;
    }
    
    
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String sayHi(Model model){
        int index = 0;
        List <Sighting> allSightings = mySight.listAllSightings();
        Sighting recentSighting = allSightings.get(0);
        List <Sighting> tenSightings = new ArrayList<>();
        for (int i=0; i<10; i++){
            tenSightings.add(allSightings.get(index));
            index = index + 1;
        }
        model.addAttribute("tenSightings", tenSightings);
        model.addAttribute("recentSighting", recentSighting);
        return "hello";
    }
}
