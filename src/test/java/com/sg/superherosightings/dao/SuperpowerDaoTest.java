/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Superpower;
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
public class SuperpowerDaoTest {

    LocationDao locationDao;
    HeroDao heroDao;
    OrganizationDao orgDao;
    SightingDao sightDao;
    SuperpowerDao powerDao;

    public SuperpowerDaoTest() {
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
        List<Superpower> powerlist = powerDao.listAllPowers();
        for (Superpower currentPower : powerlist) {
            powerDao.deletePower(currentPower.getSuperpowerId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of listAllPowers method, of class SuperpowerDao.
     */
    @Test
    public void testListAllPowers() {
        List<Superpower> powerList = powerDao.listAllPowers();
        assertEquals(0, powerList.size());

        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);

        Superpower power2 = new Superpower();
        power2.setSuperpowerName("testpowername2");
        power2.setSuperpowerDesc("testpowerdescription2");
        powerDao.addPower(power2);
        
        powerList = powerDao.listAllPowers();
        assertEquals(2, powerList.size());
        
        powerDao.deletePower(power.getSuperpowerId());
        powerDao.deletePower(power2.getSuperpowerId());
    }

    /**
     * Test of addPower method, of class SuperpowerDao.
     */
    @Test
    public void testAddSelectUpdateDelete() {

        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);

        Superpower newPower = powerDao.selectPower("testpowername");
        newPower.setSuperpowerName("updatedpowername");
        powerDao.updatePower(newPower);

        Superpower testPower = powerDao.selectPower("updatedpowername");

        assertEquals("updatedpowername", testPower.getSuperpowerName());
        assertEquals(newPower.getSuperpowerId(), testPower.getSuperpowerId());

        powerDao.deletePower(testPower.getSuperpowerId());
    }

}
