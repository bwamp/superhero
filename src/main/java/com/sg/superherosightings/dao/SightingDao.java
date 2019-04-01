/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Sighting;
import java.util.List;

/**
 *
 * @author bwamp
 */
public interface SightingDao {
    public Sighting selectSighting(int sightingId);

    public List<Sighting> listAllSightings();

    public void updateSighting(Sighting sighting);
    
    public void deleteSighting(int sightingId);
    
    public Sighting addSighting (Sighting sighting);
}
