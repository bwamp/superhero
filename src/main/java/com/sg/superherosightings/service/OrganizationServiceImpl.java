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
import com.sg.superherosightings.dto.Organization;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author bwamp
 */
public class OrganizationServiceImpl implements OrganizationService{
    
    OrganizationDao myOrg;
    
    @Inject
    public OrganizationServiceImpl (OrganizationDao myOrg){
        this.myOrg = myOrg;
    }

    @Override
    public List<Organization> orgByHero(String name) {
        return myOrg.orgByHero(name);
    }

    @Override
    public List<Organization> heroByOrg(String name) {
        return myOrg.heroByOrg(name);
    }

    @Override
    public Organization selectOrg(int orgId) {
        return myOrg.selectOrg(orgId);
    }

    @Override
    public List<Organization> listAllOrg() {
        return myOrg.listAllOrg();
    }

    @Override
    public void updateOrg(Organization org) {
        myOrg.updateOrg(org);
    }

    @Override
    public void deleteOrg(int orgId) {
        myOrg.deleteOrg(orgId);
    }

    @Override
    public Organization addOrg(Organization org) {
        return myOrg.addOrg(org);
    }
    
}
