/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Superpower;
import com.sg.superherosightings.service.HeroService;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperpowerService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author bwamp
 */
@Controller
public class LocationController {

    LocationService myLocation;
    HeroService myHero;
    OrganizationService myOrg;
    SuperpowerService myPower;
    SightingService mySight;

    @Inject
    public LocationController(LocationService myLocation, HeroService myHero, OrganizationService myOrg, SuperpowerService myPower, SightingService mySight) {
        this.myLocation = myLocation;
        this.myHero = myHero;
        this.myOrg = myOrg;
        this.myPower = myPower;
        this.mySight = mySight;
    }

    @RequestMapping(value = "/alllocations", method = RequestMethod.GET)
    public String generateAllLocations(Model model) {
        List<Location> locationList = myLocation.listAllLocations();
        List<Hero> heroSearchList = myHero.listAllHeroes();

        model.addAttribute("locationList", locationList);
        model.addAttribute("heroSearchList", heroSearchList);
        // Return the logical name of our View component
        return "location";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(HttpServletRequest request) {
        Location location = new Location();
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDesc(request.getParameter("locationDesc"));
        location.setLat(Double.parseDouble(request.getParameter("lat")));
        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));
        location.setStreet(request.getParameter("street"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));

        myLocation.addLocation(location);

        return "redirect:alllocations";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        myLocation.deleteLocation(locationId);
        return "redirect:alllocations";
    }

    @RequestMapping(value = "/editLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        Location updateLocation = myLocation.selectLocation(locationId);

        model.addAttribute("updateLocation", updateLocation);
        return "editlocation";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editHero(HttpServletRequest request) {
        Location location = new Location();
        location.setLocationId(Integer.parseInt(request.getParameter("locationId")));
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDesc(request.getParameter("locationDesc"));
        location.setLat(Double.parseDouble(request.getParameter("lat")));
        location.setLongitude(Double.parseDouble(request.getParameter("longitude")));
        location.setStreet(request.getParameter("street"));
        location.setCity(request.getParameter("city"));
        location.setState(request.getParameter("state"));
        myLocation.updateLocation(location);
        return "redirect:alllocations";
    }

    @RequestMapping(value = "/searchLocation", method = RequestMethod.POST)
    public String searchHero(HttpServletRequest request, Model model) {
        String heroName = request.getParameter("heroName");
        List<Location> searchedLocations = myLocation.locationByHero(heroName);

        List<Location> locationList = myLocation.listAllLocations();
        List<Hero> heroSearchList = myHero.listAllHeroes();

        model.addAttribute("locationList", locationList);
        model.addAttribute("heroSearchList", heroSearchList);
        model.addAttribute("searchedLocations", searchedLocations);

        return "locationbyhero";
    }

    @RequestMapping(value = "/cancelLocation", method = RequestMethod.GET)
    public String cancelLocation(HttpServletRequest request, Model model) {
        return "redirect:alllocations";
    }
}
