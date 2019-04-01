/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dto.Superpower;
import java.util.List;

/**
 *
 * @author bwamp
 */
public interface SuperpowerService {
    public Superpower selectPower(String name);

    public List<Superpower> listAllPowers();

    public void updatePower(Superpower power);
    
    public void deletePower(int superpowerId);
    
    public Superpower addPower (Superpower power );
}
