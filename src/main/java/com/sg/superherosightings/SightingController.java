/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Superpower;
import com.sg.superherosightings.service.HeroService;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperpowerService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SightingController {

    LocationService myLocation;
    HeroService myHero;
    OrganizationService myOrg;
    SuperpowerService myPower;
    SightingService mySight;

    @Inject
    public SightingController(LocationService myLocation, HeroService myHero, OrganizationService myOrg, SuperpowerService myPower, SightingService mySight) {
        this.myLocation = myLocation;
        this.myHero = myHero;
        this.myOrg = myOrg;
        this.myPower = myPower;
        this.mySight = mySight;
    }

    @RequestMapping(value = "/allsightings", method = RequestMethod.GET)
    public String generateAllSightings(Model model) {
        List<Hero> heroList = myHero.listAllHeroes();
        List<Location> locationList = myLocation.listAllLocations();
        List<Sighting> sightList = mySight.listAllSightings();

        model.addAttribute("heroList", heroList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("sightList", sightList);
        // Return the logical name of our View component
        return "sighting";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request) {
        Location location = myLocation.selectLocation(Integer.parseInt(request.getParameter("locationId")));
        Hero hero = myHero.selectHero(Integer.parseInt(request.getParameter("heroId")));
        Sighting sight = new Sighting();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sight.setSightingDate(LocalDate.parse(request.getParameter("sightDate"), dtf));
        sight.setHero(hero);
        sight.setLocation(location);
        mySight.addSighting(sight);
        myHero.newHeroSighting(hero, sight.getSightingId());

        return "redirect:allsightings";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        int sighting = Integer.parseInt(request.getParameter("sightingId"));
        mySight.deleteSighting(sighting);
        return "redirect:allsightings";
    }

    @RequestMapping(value = "/editSightDetails", method = RequestMethod.GET)
    public String displaySightDetails(HttpServletRequest request, Model model) {
        List<Hero> heroList = myHero.listAllHeroes();
        List<Location> locationList = myLocation.listAllLocations();
        Sighting sight = mySight.selectSighting(Integer.parseInt(request.getParameter("sightingId")));
        Hero hero = myHero.selectHero(Integer.parseInt(request.getParameter("heroId")));
        Location location = myLocation.selectLocation(Integer.parseInt(request.getParameter("locationId")));
        LocalDate localdate = sight.getSightingDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = localdate.format(formatter);

        model.addAttribute("heroList", heroList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("sight", sight);
        model.addAttribute("hero", hero);
        model.addAttribute("location", location);
        model.addAttribute("date", date);

        return "editsight";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request) {
        Hero hero = myHero.selectHero(Integer.parseInt(request.getParameter("heroId")));
        Location location = myLocation.selectLocation(Integer.parseInt(request.getParameter("locationId")));
        Sighting sight = new Sighting();
        sight.setHero(hero);
        sight.setLocation(location);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getParameter("sightDate"), dtf);
        sight.setSightingDate(date);
        sight.setSightingId(Integer.parseInt(request.getParameter("sightingId")));
        mySight.updateSighting(sight);

        return "redirect:allsightings";
    }

    @RequestMapping(value = "/searchSightings", method = RequestMethod.POST)
    public String searchSighting(HttpServletRequest request, Model model) throws Exception {
        String searchDate = request.getParameter("sightDate");
        List<Location> searchList = myLocation.locationHeroByDate(searchDate);

        List<Hero> heroList = myHero.listAllHeroes();
        List<Location> locationList = myLocation.listAllLocations();
        List<Sighting> sightList = mySight.listAllSightings();

        model.addAttribute("heroList", heroList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("sightList", sightList);
        model.addAttribute("searchList", searchList);

        return "sightingbydate";
    }

    @RequestMapping(value = "/cancelSighting", method = RequestMethod.GET)
        public String cancelSighting(HttpServletRequest request) {
        return "redirect:allsightings";
    }
}
