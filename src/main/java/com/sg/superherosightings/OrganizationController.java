/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
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
public class OrganizationController {
    LocationService myLocation;
    HeroService myHero;
    OrganizationService myOrg;
    SuperpowerService myPower;
    SightingService mySight;

    @Inject
    public OrganizationController(LocationService myLocation, HeroService myHero, OrganizationService myOrg, SuperpowerService myPower, SightingService mySight) {
        this.myLocation = myLocation;
        this.myHero = myHero;
        this.myOrg = myOrg;
        this.myPower = myPower;
        this.mySight = mySight;
    }

    @RequestMapping(value = "/allorganizations", method = RequestMethod.GET)
    public String generateAllOrganizations(Model model) {
        List<Organization> orgList = myOrg.listAllOrg();
        List<Location> locationList = myLocation.listAllLocations();
        List<Hero> heroList = myHero.listAllHeroes();
        
        model.addAttribute("orgList", orgList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("heroList", heroList);
        // Return the logical name of our View component
        return "organization";
    }

    @RequestMapping(value = "/createOrg", method = RequestMethod.POST)
    public String createOrg(HttpServletRequest request) {
        Organization org = new Organization();
        org.setOrganizationName(request.getParameter("organizationName"));
        org.setOrganizationDesc(request.getParameter("organizationDesc"));
        org.setLocationfkId(Integer.parseInt(request.getParameter("chooseLocation")));
        org.setPhone(request.getParameter("phone"));
        org.setEmail(request.getParameter("email"));
        
        myOrg.addOrg(org);

        return "redirect:allorganizations";
    }

    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    public String deleteOrg(HttpServletRequest request) {
        int orgId = Integer.parseInt(request.getParameter("organizationId"));
        myOrg.deleteOrg(orgId);
        return "redirect:allorganizations";
    }

    @RequestMapping(value = "/editOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        int orgId = Integer.parseInt(request.getParameter("organizationId"));
        Organization updateOrg = myOrg.selectOrg(orgId);
        Location location = myLocation.selectLocation(updateOrg.getLocationfkId());
        List<Location> locationList = myLocation.listAllLocations();
        
        model.addAttribute("updateOrg", updateOrg);
        model.addAttribute("location", location);
        model.addAttribute("locationList", locationList);
        return "editorganization";
    }

    @RequestMapping(value = "/editOrg", method = RequestMethod.POST)
    public String editOrg(HttpServletRequest request) {
        Organization org = new Organization();
        org.setOrganizationId(Integer.parseInt(request.getParameter("organizationId")));
        org.setOrganizationName(request.getParameter("organizationName"));
        org.setOrganizationDesc(request.getParameter("organizationDesc"));
        org.setEmail(request.getParameter("email"));
        org.setPhone(request.getParameter("phone"));
        org.setLocationfkId(Integer.parseInt(request.getParameter("locationId")));
        
        myOrg.updateOrg(org);
        return "redirect:allorganizations";
    }
    
    @RequestMapping(value = "/searchOrg", method = RequestMethod.POST)
    public String searchOrganization(HttpServletRequest request, Model model) {
        String heroName = request.getParameter("heroName");
        List <Organization> searchedOrganizations = myOrg.orgByHero(heroName);
        List<Organization> orgList = myOrg.listAllOrg();
        List<Location> locationList = myLocation.listAllLocations();
        List<Hero> heroList = myHero.listAllHeroes();
        
        model.addAttribute("orgList", orgList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("heroList", heroList);
        model.addAttribute("searchedOrganizations", searchedOrganizations);
        
        return "organizationbyhero";
    }
    @RequestMapping(value = "/cancelOrg", method = RequestMethod.GET)
    public String cancelOrg(HttpServletRequest request) {
        return "redirect:allorganizations";
    }
    @RequestMapping(value = "/assignOrg", method = RequestMethod.POST)
    public String assignOrg(HttpServletRequest request) {
        
        Hero hero = myHero.selectHero(Integer.parseInt(request.getParameter("myhero")));
        Organization org = myOrg.selectOrg(Integer.parseInt(request.getParameter("myorg")));
        
        myHero.newHeroOrg(hero, org.getOrganizationId());
        return "redirect:allorganizations";
    }
}
