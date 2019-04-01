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
import com.sg.superherosightings.dto.Location;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author bwamp
 */
public class LocationServiceImpl implements LocationService {

    
    LocationDao myLocation;
    
    @Inject
    public LocationServiceImpl (LocationDao myLocation){
        this.myLocation = myLocation;
    }
    
    @Override
    public List<Location> locationHeroByDate(String date) throws Exception {
        return myLocation.locationHeroByDate(date);
    }

    @Override
    public List<Location> locationByHero(String name) {
        return myLocation.locationByHero(name);
    }

    @Override
    public List<Location> heroByLocation(String name) {
        return myLocation.heroByLocation(name);
    }

    @Override
    public List<Location> listAllLocations() {
        return myLocation.listAllLocations();
    }

    @Override
    public void updateLocation(Location location) {
        myLocation.updateLocation(location);
    }

    @Override
    public void deleteLocation(int locationId) {
        myLocation.deleteLocation(locationId);
    }

    @Override
    public Location addLocation(Location location) {
        return myLocation.addLocation(location);
    }
    
    @Override
    public Location selectLocation (int locationId){
        return myLocation.selectLocation(locationId);
    }
    
}
