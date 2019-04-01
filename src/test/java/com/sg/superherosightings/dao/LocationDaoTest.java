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
public class LocationDaoTest {
    
    LocationDao dao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightDao;
    SuperpowerDao powerDao;
    
    public LocationDaoTest() {
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
        dao = ctx.getBean("LocationDao", LocationDao.class);
        heroDao = ctx.getBean("HeroDao", HeroDao.class);
        orgDao = ctx.getBean("OrganizationDao", OrganizationDao.class);
        sightDao = ctx.getBean("SightingDao", SightingDao.class);
        powerDao = ctx.getBean("SuperpowerDao", SuperpowerDao.class);
        
        // remove all
        List<Location> locationlist = dao.listAllLocations();
        for (Location currentLocation : locationlist) {
            dao.deleteLocation(currentLocation.getLocationId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of locationHeroByDate method, of class LocationDao.
     */
    @Test
    public void testLocationHeroByDate() throws Exception {
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
        Location newLocation = dao.addLocation(location);
        
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
        
        LocalDate ld = newSight.getSightingDate();
        String datetest = ld.format(dtf);

        List <Location> testList = dao.locationHeroByDate(datetest);
        assertEquals(1, testList.size());
        
        dao.deleteLocation(newLocation.getLocationId());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(newhero.getHeroId());
        orgDao.deleteOrg(newOrg.getOrganizationId());
        sightDao.deleteSighting(newSight.getSightingId());
        
    }

    /**
     * Test of locationByHero method, of class LocationDao.
     */
    @Test
    public void testLocationByHero() {
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
        Location newLocation = dao.addLocation(location);
        
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

        List <Location> testList = dao.locationByHero(newhero.getHeroName());
        assertEquals(1, testList.size());
        
        dao.deleteLocation(newLocation.getLocationId());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(newhero.getHeroId());
        orgDao.deleteOrg(newOrg.getOrganizationId());
        sightDao.deleteSighting(newSight.getSightingId());
    }

    /**
     * Test of heroByLocation method, of class LocationDao.
     */
    @Test
    public void testHeroByLocation() {
        
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
        Location newLocation = dao.addLocation(location);
        
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

        List <Location> testList = dao.heroByLocation(newLocation.getLocationName());
        assertEquals(1, testList.size());
        
        dao.deleteLocation(newLocation.getLocationId());
        powerDao.deletePower(testpower.getSuperpowerId());
        heroDao.deleteHero(newhero.getHeroId());
        orgDao.deleteOrg(newOrg.getOrganizationId());
        sightDao.deleteSighting(newSight.getSightingId());
    }

    /**
     * Test of updateLocation method, of class LocationDao.
     */
    @Test
    public void testUpdateList() {
        List <Location> listLocation = dao.listAllLocations();
        assertEquals(0,listLocation.size());
        
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        Location newLocation = dao.addLocation(location);
        
        newLocation.setLocationName("updated name");
        dao.updateLocation(newLocation);
        
        List <Location> testList = dao.listAllLocations();
        assertEquals(1,testList.size());
        
        dao.deleteLocation(newLocation.getLocationId());
    }
        
    /**
     * Test of addLocation method, of class LocationDao.
     */
    @Test
    public void testAddSelectDelete() {
        Location location = new Location();
        location.setCity("test city");
        location.setLocationDesc("test description");
        location.setLocationName("test name");
        location.setLat(11);
        location.setLongitude(12);
        location.setState("test state");
        location.setStreet("test street");
        
        Location newLocation = dao.addLocation(location);
        assertEquals("test description", newLocation.getLocationDesc());
        dao.deleteLocation(newLocation.getLocationId());
    }
    
}
