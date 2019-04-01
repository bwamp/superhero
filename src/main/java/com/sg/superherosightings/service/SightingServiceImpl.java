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
import com.sg.superherosightings.dto.Sighting;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author bwamp
 */
public class SightingServiceImpl implements SightingService {
    
    SightingDao mySight;
    
    @Inject
    public SightingServiceImpl (SightingDao mySight){
        this.mySight = mySight;
    }

    @Override
    public Sighting selectSighting(int sightingId) {
        return mySight.selectSighting(sightingId);
    }

    @Override
    public List<Sighting> listAllSightings() {
        return mySight.listAllSightings();
    }

    @Override
    public void updateSighting(Sighting sighting) {
        mySight.updateSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        mySight.deleteSighting(sightingId);
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return mySight.addSighting(sighting);
    }
    
}
