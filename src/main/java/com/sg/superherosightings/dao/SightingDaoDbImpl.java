/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Sighting;
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
public class SightingDaoDbImpl implements SightingDao {
     private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_ADD
            = "insert into sighting (location_id, sightdate) "
            + "values (?, ?)";
    private static final String SQL_UPDATE
            = "update sighting set location_id = ?, sightdate = ? where sighting_id = ? ";
    private static final String SQL_DELETE
            = "delete from sighting where sighting_id = ? ";
    private static final String SQL_LISTALLSIGHTS
            = "select a.*, b.hero_id, b.hero_name, b.hero_description, c.location_id, c.location_name, c.location_description"
            + " from sighting as a inner join hero_sighting as z on z.sighting_id = a.sighting_id inner join hero as b on z.hero_id = b.hero_id"
            + " inner join location as c on a.location_id = c.location_id order by sightdate desc";
    private static final String SQL_SELECTSIGHT
            = "select * from sighting where sighting_id = ?";
    
    @Override
    public Sighting selectSighting(int sightingId) {
        return jdbcTemplate.queryForObject(SQL_SELECTSIGHT, new SightMapper(), sightingId);
    }

    @Override
    public List<Sighting> listAllSightings() {
        return jdbcTemplate.query(SQL_LISTALLSIGHTS, new AllSightMapper());
    }

    @Override
    public void updateSighting(Sighting sight) {
        java.sql.Date sqlDate = java.sql.Date.valueOf(sight.getSightingDate());
        Location location = sight.getLocation();
        int locationId = location.getLocationId();
        jdbcTemplate.update(SQL_UPDATE, locationId, sqlDate, sight.getSightingId());
    }

    @Override
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE, sightingId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting addSighting(Sighting sight) {
        Location location = sight.getLocation();
        int locationId = location.getLocationId();
        java.sql.Date sqlDate = java.sql.Date.valueOf(sight.getSightingDate());
            jdbcTemplate.update(SQL_ADD,
                locationId, sqlDate);
                
        // query the database for the id that was just assigned
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the object and return it
        sight.setSightingId(newId);
        return sight;
    }

    private static final class SightMapper implements RowMapper<Sighting> {

        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sight = new Sighting();
            sight.setSightingId(rs.getInt("sighting_id"));
            sight.setSightingDate(rs.getDate("sightdate").toLocalDate());
            Location location = new Location();
            location.setLocationId(rs.getInt("location_id"));
            sight.setLocation(location);
            return sight;
        }
    }
    private static final class AllSightMapper implements RowMapper<Sighting> {

        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sight = new Sighting();
            sight.setSightingId(rs.getInt("sighting_id"));
            sight.setSightingDate(rs.getDate("sightdate").toLocalDate());
            Location location = new Location();
            location.setLocationId(rs.getInt("location_id"));
            location.setLocationName(rs.getString("location_name"));
            location.setLocationDesc(rs.getString("location_description"));
            sight.setLocation(location);
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("hero_id"));
            hero.setHeroName(rs.getString("hero_name"));
            hero.setHeroDesc(rs.getString("hero_description"));
            sight.setHero(hero);
            return sight;
        }
    }
}
