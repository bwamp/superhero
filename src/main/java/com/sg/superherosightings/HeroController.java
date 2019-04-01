/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Superpower;
import com.sg.superherosightings.service.HeroService;
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SightingService;
import com.sg.superherosightings.service.SuperpowerService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author bwamp
 */
@Controller
public class HeroController {

    LocationService myLocation;
    HeroService myHero;
    OrganizationService myOrg;
    SuperpowerService myPower;
    SightingService mySight;

    @Inject
    public HeroController(LocationService myLocation, HeroService myHero, OrganizationService myOrg, SuperpowerService myPower, SightingService mySight) {
        this.myLocation = myLocation;
        this.myHero = myHero;
        this.myOrg = myOrg;
        this.myPower = myPower;
        this.mySight = mySight;
    }

    @RequestMapping(value = "/allheroes", method = RequestMethod.GET)
    public String generateAllHeros(Model model) {
        List<Hero> heroList = myHero.listAllHeroes();
        List<Organization> orgList = myOrg.listAllOrg();
        List<Superpower> powerList = myPower.listAllPowers();
        List<Location> locationList = myLocation.listAllLocations();

        // Put the List of Contacts on the Model
        model.addAttribute("heroList", heroList);
        model.addAttribute("orgList", orgList);
        model.addAttribute("powerList", powerList);
        model.addAttribute("locationList", locationList);
        // Return the logical name of our View component
        return "hero";
    }

    @RequestMapping(value = "/createHero", method = RequestMethod.POST)
    public String createHero(HttpServletRequest request) {
        Superpower power = myPower.selectPower(request.getParameter("chosePower"));

        Hero hero = new Hero();
        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDesc(request.getParameter("heroDesc"));
        hero.setSuperpower(power);
        Hero newHero = myHero.addHero(hero, power);
        String test = request.getParameter("orgId");
        myHero.newHeroOrg(newHero, Integer.parseInt(request.getParameter("orgId")));

        return "redirect:allheroes";
    }

    @RequestMapping(value = "/deleteHero", method = RequestMethod.GET)
    public String deleteHero(HttpServletRequest request) {
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        myHero.deleteHero(heroId);
        return "redirect:allheroes";
    }

    @RequestMapping(value = "/editHeroDetails", method = RequestMethod.GET)
    public String displayHeroDetails(HttpServletRequest request, Model model) {
       int hero = Integer.parseInt(request.getParameter("heroId"));
       Hero updateHero = myHero.selectHero(hero);
       Superpower power = updateHero.getSuperpower();
       List<Organization> orgList = myOrg.listAllOrg();
       List<Superpower> powerList = myPower.listAllPowers();
       
       
               
       model.addAttribute("updateHero", updateHero);
       model.addAttribute("orgList", orgList);
       model.addAttribute("powerList", powerList);
       model.addAttribute("power", power);
       
       

        return "edithero";
    }

    @RequestMapping(value = "/editHero", method = RequestMethod.POST)
    public String editHero(HttpServletRequest request) {
        Hero hero = new Hero();
        hero.setHeroId(Integer.parseInt(request.getParameter("heroId")));
        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDesc(request.getParameter("heroDesc"));
        String powerName = request.getParameter("superpowerName");
        Superpower power = myPower.selectPower(request.getParameter("superpowerName"));
        hero.setSuperpower(power);
        myHero.updateHero(hero, power);
        
        return "redirect:allheroes";
    }
    @RequestMapping(value = "/searchHero", method = RequestMethod.POST)
    public String searchHero(HttpServletRequest request, Model model) {
        String locationName = request.getParameter("locationName");
        List <Location> searchedHero = myLocation.heroByLocation(locationName);
        
        List<Hero> heroList = myHero.listAllHeroes();
        List<Organization> orgList = myOrg.listAllOrg();
        List<Superpower> powerList = myPower.listAllPowers();
        List<Location> locationList = myLocation.listAllLocations();

        // Put the List of Contacts on the Model
        model.addAttribute("heroList", heroList);
        model.addAttribute("orgList", orgList);
        model.addAttribute("powerList", powerList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("searchedHero", searchedHero);
        
        return "herobylocation";
    }
    @RequestMapping(value = "/cancelHero", method = RequestMethod.GET)
    public String cancelHero(HttpServletRequest request, Model model) {
        
        return "redirect:allheroes";
    }
}
