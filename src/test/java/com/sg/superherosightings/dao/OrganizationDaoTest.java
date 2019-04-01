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
public class OrganizationDaoTest {
    
    LocationDao locationDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightDao;
    SuperpowerDao powerDao;
    
    public OrganizationDaoTest() {
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
        List<Organization> organizationlist = orgDao.listAllOrg();
        for (Organization currentOrg : organizationlist) {
            orgDao.deleteOrg(currentOrg.getOrganizationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of orgByHero method, of class OrganizationDao.
     */
    @Test
    public void testOrgByHero() {
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        Superpower testpower = powerDao.addPower(power);
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        Hero newhero = heroDao.addHero(hero, testpower);
        
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        location.setHero(hero);
        Location newLocation = locationDao.addLocation(location);
        
        Organization org = new Organization();
        org.setEmail("test email");
        org.setLocationfkId(newLocation.getLocationId());
        org.setOrganizationDesc("test description");
        org.setOrganizationName("test name");
        org.setPhone("9132560832");
        Organization newOrg = orgDao.addOrg(org);
                
        Sighting sight = new Sighting();
        sight.setLocation(newLocation);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sight.setSightingDate(LocalDate.parse("2020-12-28", dtf));
        Sighting newSight = sightDao.addSighting(sight);
        
        heroDao.newHeroSighting(newhero, newSight.getSightingId());
        heroDao.newHeroOrg(newhero, newOrg.getOrganizationId());

        List <Organization> testList = orgDao.orgByHero("testname");
        assertEquals(1, testList.size());
        
        locationDao.deleteLocation(newLocation.getLocationId());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(newhero.getHeroId());
        orgDao.deleteOrg(newOrg.getOrganizationId());
        sightDao.deleteSighting(newSight.getSightingId());
    }

    /**
     * Test of heroByOrg method, of class OrganizationDao.
     */
    @Test
    public void testHeroByOrg() {
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        Superpower testpower = powerDao.addPower(power);
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        Hero newhero = heroDao.addHero(hero, testpower);
        
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        location.setHero(hero);
        Location newLocation = locationDao.addLocation(location);
        
        Organization org = new Organization();
        org.setEmail("test email");
        org.setLocationfkId(newLocation.getLocationId());
        org.setOrganizationDesc("test description");
        org.setOrganizationName("test name");
        org.setPhone("9132560832");
        Organization newOrg = orgDao.addOrg(org);
                
        Sighting sight = new Sighting();
        sight.setLocation(newLocation);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sight.setSightingDate(LocalDate.parse("2020-12-28", dtf));
        Sighting newSight = sightDao.addSighting(sight);
        
        heroDao.newHeroSighting(newhero, newSight.getSightingId());
        heroDao.newHeroOrg(newhero, newOrg.getOrganizationId());

        List <Organization> testList = orgDao.heroByOrg("test name");
        assertEquals(1, testList.size());
        
        locationDao.deleteLocation(newLocation.getLocationId());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(newhero.getHeroId());
        orgDao.deleteOrg(newOrg.getOrganizationId());
        sightDao.deleteSighting(newSight.getSightingId());
        
    }

    /**
     * Test of listAllOrg method, of class OrganizationDao.
     */
    @Test
    public void testListAllOrg() {
               Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        Location newLocation = locationDao.addLocation(location);
        
        Organization org = new Organization ();
        org.setEmail("test email");
        org.setOrganizationDesc("test description");
        org.setOrganizationName("test name");
        org.setPhone("9132560832");
        org.setLocationfkId(newLocation.getLocationId());
        Organization newOrg = orgDao.addOrg(org);
        newOrg.setOrganizationName("updated name");
        orgDao.updateOrg(newOrg);
        Organization testOrg = orgDao.selectOrg(newOrg.getOrganizationId());
        
        List <Organization> orgList = orgDao.listAllOrg();
        
        assertEquals(1, orgList.size());
        
        orgDao.deleteOrg(testOrg.getOrganizationId());
        locationDao.deleteLocation(newLocation.getLocationId());
    }

    /**
     * Test of addOrg method, of class OrganizationDao.
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
        
        Organization org = new Organization ();
        org.setEmail("test email");
        org.setOrganizationDesc("test description");
        org.setOrganizationName("test name");
        org.setPhone("9132560832");
        org.setLocationfkId(newLocation.getLocationId());
        Organization newOrg = orgDao.addOrg(org);
        newOrg.setOrganizationName("updated name");
        orgDao.updateOrg(newOrg);
        Organization testOrg = orgDao.selectOrg(newOrg.getOrganizationId());
        
        assertEquals("updated name", testOrg.getOrganizationName());
        assertEquals("9132560832", newOrg.getPhone());
        
        orgDao.deleteOrg(testOrg.getOrganizationId());
        locationDao.deleteLocation(newLocation.getLocationId());
        
        
        
    }
    
}
