/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Location;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bwamp
 */
public interface LocationDao {
    
    public List <Location> locationHeroByDate (String date)throws Exception;

    public List<Location> locationByHero(String name);
            
    public List<Location> heroByLocation(String name);

    public List<Location> listAllLocations();

    public void updateLocation(Location location);
    
    public void deleteLocation(int locationId);
    
    public Location addLocation (Location location);
    
    public Location selectLocation (int locationId);
}
