/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.dao.SuperpowerDao;
import com.sg.superherosightings.dto.Superpower;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author bwamp
 */
public class SuperpowerServiceImpl implements SuperpowerService {

    SuperpowerDao myPower;
    
    @Inject
    public SuperpowerServiceImpl (SuperpowerDao myPower){
        this.myPower = myPower;
    }
    @Override
    public Superpower selectPower(String name) {
        return myPower.selectPower(name);
    }

    @Override
    public List<Superpower> listAllPowers() {
        return myPower.listAllPowers();
    }

    @Override
    public void updatePower(Superpower power) {
        myPower.updatePower(power);
    }

    @Override
    public void deletePower(int superpowerId) {
        myPower.deletePower(superpowerId);
    }

    @Override
    public Superpower addPower(Superpower power) {
        return myPower.addPower(power);
    }
    
}
