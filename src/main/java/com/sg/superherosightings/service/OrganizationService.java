/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dto.Organization;
import java.util.List;

/**
 *
 * @author bwamp
 */
public interface OrganizationService {
    public List<Organization> orgByHero(String name);
            
    public List<Organization> heroByOrg(String name);
    
    public Organization selectOrg(int orgId);

    public List<Organization> listAllOrg();

    public void updateOrg(Organization org);
    
    public void deleteOrg(int orgId);
    
    public Organization addOrg (Organization org);
}
