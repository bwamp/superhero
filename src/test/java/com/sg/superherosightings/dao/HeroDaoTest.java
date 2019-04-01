/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Superpower;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bwamp
 */
public class HeroDaoTest {
    
    public HeroDaoTest() {
    }
    HeroDao dao;
    SuperpowerDao powerDao;
    
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
        dao = ctx.getBean("HeroDao", HeroDao.class);
        powerDao = ctx.getBean("SuperpowerDao", SuperpowerDao.class);
        
        // remove all
        List<Hero> herolist = dao.listAllHeroes();
        for (Hero currentHero : herolist) {
            dao.deleteHero(currentHero.getHeroId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of selectHero method, of class HeroDao.
     */
    @Test
    public void addSelectDeleteHeroPower() {
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);
        Superpower testpower = powerDao.selectPower("testpowername");
        
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        Hero testHero = dao.addHero(hero, testpower);
        
        assertEquals("testname", testHero.getHeroName());
        dao.deleteHero(testHero.getHeroId());
        powerDao.deletePower(testpower.getSuperpowerId());
        
    }

    /**
     * Test of listAllHeroes method, of class HeroDao.
     */
    @Test
    public void testListAllHeroes() {
        List <Hero> testList = dao.listAllHeroes();
        assertEquals(0, testList.size());
        
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);
        Superpower testpower = powerDao.selectPower("testpowername");
        
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        dao.addHero(hero, testpower);
        
        Superpower power2 = new Superpower();
        power2.setSuperpowerName("testpowername2");
        power2.setSuperpowerDesc("testpowerdescription2");
        powerDao.addPower(power2);
        Superpower testpower2 = powerDao.selectPower("testpowername2");
        
        
        Hero hero2 = new Hero();
        hero2.setHeroName("testname2");
        hero2.setHeroDesc("testdecription2");
        hero2.setSuperpower(testpower2);
        dao.addHero(hero2, testpower2);
        
        List<Hero> testList2 = dao.listAllHeroes();
        
        assertEquals(2,testList2.size());
   
        Hero deletehero = testList2.get(0);
        Hero deletehero2 = testList2.get(1);
        dao.deleteHero(deletehero.getHeroId());
        dao.deleteHero(deletehero2.getHeroId());
        powerDao.deletePower(testpower.getSuperpowerId());
        powerDao.deletePower(testpower2.getSuperpowerId());
    }

    /**
     * Test of updateHero method, of class HeroDao.
     */
    @Test
    public void testUpdateHero() {
        Superpower power = new Superpower();
        power.setSuperpowerName("testpowername");
        power.setSuperpowerDesc("testpowerdescription");
        powerDao.addPower(power);
        Superpower testpower = powerDao.selectPower("testpowername");
        
        
        Hero hero = new Hero();
        hero.setHeroName("testname");
        hero.setHeroDesc("testdecription");
        hero.setSuperpower(testpower);
        Hero updateHero = dao.addHero(hero, testpower);
        
        updateHero.setHeroName("updatedname");
        dao.updateHero(updateHero, testpower);
        Hero testHero = dao.selectHero(updateHero.getHeroId());
        assertEquals(updateHero.getHeroId(), testHero.getHeroId());
        
        powerDao.deletePower(testpower.getSuperpowerId());
        dao.deleteHero(testHero.getHeroId());
    }
    
}
