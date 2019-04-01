/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Superpower;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bwamp
 */
public class SuperpowerServiceTest {
    LocationService locationService;
    HeroService heroService;
    OrganizationService orgService;
    SightingService sightService;
    SuperpowerService powerService;
    
    public SuperpowerServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");
        locationService = ctx.getBean("LocationService", LocationService.class);
        heroService = ctx.getBean("HeroService", HeroService.class);
        orgService = ctx.getBean("OrganizationService", OrganizationService.class);
        sightService = ctx.getBean("SightingService", SightingService.class);
        powerService = ctx.getBean("SuperpowerService", SuperpowerService.class);

        
            Superpower power = new Superpower();
            power.setSuperpowerName("testpowername");
            power.setSuperpowerDesc("testpowerdescription");
            Superpower testpower = powerService.addPower(power);

            Hero hero = new Hero();
            hero.setHeroName("testname");
            hero.setHeroDesc("testdescription");
            hero.setSuperpower(testpower);
            Hero newhero = heroService.addHero(hero, testpower);

            Location location = new Location();
            location.setCity("test city");
            location.setLocationDesc("test description");
            location.setLocationName("test name");
            location.setLat(11);
            location.setLongitude(12);
            location.setState("test state");
            location.setStreet("test street");
            location.setHero(hero);
            Location newLocation = locationService.addLocation(location);

            Organization org = new Organization();
            org.setEmail("test email");
            org.setLocationfkId(newLocation.getLocationId());
            org.setOrganizationDesc("test description");
            org.setOrganizationName("test org");
            org.setPhone("9132560832");
            Organization newOrg = orgService.addOrg(org);

            Sighting sight = new Sighting();
            sight.setLocation(newLocation);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            sight.setSightingDate(LocalDate.parse("2020-12-28", dtf));
            Sighting newSight = sightService.addSighting(sight);

            heroService.newHeroSighting(newhero, newSight.getSightingId());
            heroService.newHeroOrg(newhero, newOrg.getOrganizationId());
    }
    
    @After
    public void tearDown() {
        List<Hero> herolist = heroService.listAllHeroes();
        for (Hero currentHero : herolist) {
            heroService.deleteHero(currentHero.getHeroId());
        }
        List<Location> locationlist = locationService.listAllLocations();
        for (Location currentLocation : locationlist) {
            locationService.deleteLocation(currentLocation.getLocationId());
        }
        List<Organization> organizationlist = orgService.listAllOrg();
        for (Organization currentorganization : organizationlist) {
            orgService.deleteOrg(currentorganization.getOrganizationId());
        }
        List<Sighting> sightlist = sightService.listAllSightings();
        for (Sighting currentSight : sightlist) {
            sightService.deleteSighting(currentSight.getSightingId());
        }
        List<Superpower> powerlist = powerService.listAllPowers();
        for (Superpower currentPower : powerlist) {
            powerService.deletePower(currentPower.getSuperpowerId());
        }
    }

    /**
     * Test of updatePower method, of class SuperpowerService.
     */
    @Test
    public void testUpdatePower() {
        // list add select delete tested in the set up and tear down
        
        Superpower power = powerService.selectPower("testpowername");
        power.setSuperpowerName("updatedpowername");
        powerService.updatePower(power);
        Superpower testpower = powerService.selectPower("updatedpowername");
        
        assertEquals(testpower.getSuperpowerId(), power.getSuperpowerId());
    }

}
