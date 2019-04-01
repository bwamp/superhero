/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.HeroDao;
import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Superpower;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author bwamp
 */
public class HeroServiceImpl implements HeroService{
    HeroDao myHero;
    
    @Inject
    public HeroServiceImpl (HeroDao myHero){
        this.myHero = myHero;
    }

    @Override
    public Hero selectHero(int heroId) {
       return myHero.selectHero(heroId);
    }

    @Override
    public List<Hero> listAllHeroes() {
        return myHero.listAllHeroes();
    }

    @Override
    public void updateHero(Hero hero, Superpower superpower) {
        myHero.updateHero(hero, superpower);
    }

    @Override
    public void deleteHero(int heroId) {
        myHero.deleteHero(heroId);
    }

    @Override
    public Hero addHero(Hero hero, Superpower superpower) {
        return myHero.addHero(hero, superpower);
    }

    @Override
    public void newHeroSighting(Hero hero, int sightNumber) {
        myHero.newHeroSighting(hero, sightNumber);
    }

    @Override
    public void newHeroOrg(Hero hero, int orgNumber) {
        myHero.newHeroOrg(hero, orgNumber);
    }
    
}
