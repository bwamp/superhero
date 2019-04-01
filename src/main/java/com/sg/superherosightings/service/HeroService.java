/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Superpower;
import java.util.List;

/**
 *
 * @author bwamp
 */
public interface HeroService {
    public Hero selectHero(int heroId);

    public List<Hero> listAllHeroes();

    public void updateHero(Hero hero, Superpower superpower);
    
    public void deleteHero(int heroId);
    
    public Hero addHero (Hero hero, Superpower superpower );
    
    public void newHeroSighting (Hero hero, int sightNumber);
    
    public void newHeroOrg (Hero hero, int orgNumber);
}
