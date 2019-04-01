/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bwamp
 */
public class OrganizationDaoDbImpl implements OrganizationDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_ORGBYHERO
            = "select a.*, b.hero_id, b.hero_name, b.hero_description from organization as a "
            + "inner join hero_org as z on z.organization_id = a.organization_id inner join hero as b on z.herofk_id = b.hero_id "
            + "where b.hero_name = ?";
    private static final String SQL_HEROBYORG
            = "select a.*, b.hero_id, b.hero_name, b.hero_description from organization as a "
            + "inner join hero_org as z on z.organization_id = a.organization_id inner join hero as b on z.herofk_id = b.hero_id "
            + "where a.organization_name = ?";
    private static final String SQL_SELECTORG
            = "select * from organization where organization_id = ?";
    private static final String SQL_SELECTALLORG
            = "select * from organization";
    private static final String SQL_UPDATE
            = "update organization set organization_name = ?, organization_description = ?, locationfk_id = ?, phone = ?, email = ? "
            + "where organization_id = ? ";
    private static final String SQL_DELETE
            = "delete from organization where organization_id = ? ";
    private static final String SQL_ADD
            = "insert into organization (organization_name, organization_description, locationfk_id, phone, email) "
            + "values (?, ?, ?, ?, ?)";

    @Override
    public List<Organization> orgByHero(String name) {
        return jdbcTemplate.query(SQL_ORGBYHERO, new OrgMapper(), name);
    }

    @Override
    public List<Organization> heroByOrg(String name) {
        return jdbcTemplate.query(SQL_HEROBYORG, new OrgMapper(), name);
    }

    @Override
    public Organization selectOrg(int orgId) {
        return jdbcTemplate.queryForObject(SQL_SELECTORG, new SingleOrgMapper(), orgId);
    }

    @Override
    public List<Organization> listAllOrg() {
        return jdbcTemplate.query(SQL_SELECTALLORG, new SingleOrgMapper());
    }

    @Override
    public void updateOrg(Organization org) {
        jdbcTemplate.update(SQL_UPDATE, org.getOrganizationName(), org.getOrganizationDesc(), org.getLocationfkId(), org.getPhone(), org.getEmail(), org.getOrganizationId());
    }

    @Override
    public void deleteOrg(int orgId) {
        jdbcTemplate.update(SQL_DELETE, orgId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization addOrg(Organization org) {
        jdbcTemplate.update(SQL_ADD,
                org.getOrganizationName(), org.getOrganizationDesc(), org.getLocationfkId(), org.getPhone(), org.getEmail());

        // query the database for the id that was just assigned
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the object and return it
        org.setOrganizationId(newId);
        return org;
    }

    private static final class OrgMapper implements RowMapper<Organization> {

        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationId(rs.getInt("organization_id"));
            org.setOrganizationName(rs.getString("organization_name"));
            org.setOrganizationDesc(rs.getString("organization_description"));
            org.setLocationfkId(rs.getInt("locationfk_id"));
            org.setPhone(rs.getString("phone"));
            org.setEmail(rs.getString("email"));

            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("hero_id"));
            hero.setHeroName(rs.getString("hero_name"));
            hero.setHeroDesc(rs.getString("hero_description"));

            org.setHero(hero);

            return org;
        }
    }

    private static final class SingleOrgMapper implements RowMapper<Organization> {

        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationId(rs.getInt("organization_id"));
            org.setOrganizationName(rs.getString("organization_name"));
            org.setOrganizationDesc(rs.getString("organization_description"));
            org.setLocationfkId(rs.getInt("locationfk_id"));
            org.setPhone(rs.getString("phone"));
            org.setEmail(rs.getString("email"));

            return org;
        }
    }

}
