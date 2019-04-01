/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

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
public class SightingDaoTest {
    LocationDao locationDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightDao;
    SuperpowerDao powerDao;
    
    public SightingDaoTest() {
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
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        orgDao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        sightDao = ctx.getBean("SightingDao", SightingDao.class);
        powerDao = ctx.getBean("SuperpowerDao", SuperpowerDao.class);
        
        // remove all
        List<Sighting> sightlist = sightDao.listAllSightings();
        for (Sighting currentSight : sightlist) {
            sightDao.deleteSighting(currentSight.getSightingId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of listAllSightings method, of class SightingDao.
     */
    @Test
    public void testListAllSightings() {
        List<Sighting> sightingList = sightDao.listAllSightings();
        assertEquals(0,sightingList.size());
        
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        Location newLocation = locationDao.addLocation(location);
        
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);
        Superpower testpower = powerDao.selectPower("testpowername");
        
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        Hero testHero = heroDao.addHero(hero, testpower);
        
        Sighting sight = new Sighting();
        sight.setLocation(newLocation);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sight.setSightingDate(LocalDate.parse("2020-12-28", dtf));
        Sighting newSight = sightDao.addSighting(sight);
        heroDao.newHeroSighting(testHero, newSight.getSightingId());
        
        sightingList = sightDao.listAllSightings();
        assertEquals(1, sightingList.size());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(testHero.getHeroId());
        locationDao.deleteLocation(newLocation.getLocationId());
    }

    /**
     * Test of addSighting method, of class SightingDao.
     */
    @Test
    public void testAddSelectUpdateDelete() {
        
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        Location newLocation = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setCity("update city");
        location2.setLocationDesc("update description");
        location2.setLocationName("update name");
        location2.setLat(12);
        location2.setLongitude(11);
        location2.setState("update state");
        location2.setStreet("update street");
        Location newLocation2 = locationDao.addLocation(location2);
        
        Sighting sight = new Sighting();
        sight.setLocation(newLocation);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sight.setSightingDate(LocalDate.parse("2020-12-28", dtf));
        
        Sighting newSight = sightDao.addSighting(sight);
        newSight.setLocation(newLocation2);
        sightDao.updateSighting(newSight);
        
        Sighting testSight = sightDao.selectSighting(newSight.getSightingId());
        Location confirmlocation = testSight.getLocation();
        int locationId = confirmlocation.getLocationId();
        
        assertEquals(newLocation2.getLocationId(), locationId);
        
        sightDao.deleteSighting(testSight.getSightingId());
        locationDao.deleteLocation(newLocation.getLocationId());
        locationDao.deleteLocation(newLocation2.getLocationId());
    }    
}
