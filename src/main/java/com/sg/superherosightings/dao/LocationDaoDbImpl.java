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
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bwamp
 */
public class LocationDaoDbImpl implements LocationDao {

    private static final String SQL_LOCATIONHEROBYDATE
            = "select distinct a.*, b.hero_id, b.hero_name, z.sighting_id, z.sightdate from location as a "
            + "inner join sighting as z on z.location_id = a.location_id "
            + "inner join hero_sighting as y on y.sighting_id = z.sighting_id inner join hero as b on y.hero_id = b.hero_id "
            + "where z.sightdate = ?";
    private static final String SQL_LOCATIONSBYHERO
            = "select distinct a.*, b.hero_id, b.hero_name, z.sightdate, z.sighting_id from location as a "
            + "inner join sighting as z on z.location_id = a.location_id "
            + "inner join hero_sighting as y on y.sighting_id = z.sighting_id inner join hero as b on y.hero_id = b.hero_id "
            + "where b.hero_name = ?";
    private static final String SQL_HEROBYLOCATION
            = "select distinct a.*, b.hero_id, b.hero_name, z.sightdate, z.sighting_id from location as a "
            + "inner join sighting as z on z.location_id = a.location_id "
            + "inner join hero_sighting as y on y.sighting_id = z.sighting_id inner join hero as b on y.hero_id = b.hero_id "
            + "where a.location_name = ?";
    private static final String SQL_SELECTALLLOCATIONS
            = "Select * from location";
    private static final String SQL_DELETE
            = "delete from location where location_id = ?";
    private static final String SQL_UPDATE
            = "update location set location_name = ?, location_description = ?, lat = ?, longitue = ?, "
            + "street = ?, state = ?, city = ? where location_id = ?";
    private static final String SQL_ADD
            = "insert into location (location_name, location_description, lat, longitue, street, state, city) "
            + "values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT
            = "select * from location where location_id = ?";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
// add a location with a sighting and hero. should be able to do by having the services communicate this to the loactiondao
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public Location addLocationDate(Location location){
//        Location newLocation = addLocation(location);
//        jdbcTemplate.update(SQL)
//    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location addLocation(Location location) {
        jdbcTemplate.update(SQL_ADD,
                location.getLocationName(),
                location.getLocationDesc(),
                location.getLat(),
                location.getLongitude(),
                location.getStreet(),
                location.getState(),
                location.getCity());
        // query the database for the id that was just assigned
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the object and return it
        location.setLocationId(newId);
        return location;
    }

    @Override
    public void deleteLocation(int locationId) {
        jdbcTemplate.update(SQL_DELETE, locationId);
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE,
                location.getLocationName(),
                location.getLocationDesc(),
                location.getLat(),
                location.getLongitude(),
                location.getStreet(),
                location.getState(),
                location.getCity(),
                location.getLocationId());
    }

    @Override
    public List<Location> listAllLocations() {
        return jdbcTemplate.query(SQL_SELECTALLLOCATIONS, new AllLocationMapper());
    }
    
    @Override
    public Location selectLocation (int locationId){
        return jdbcTemplate.queryForObject(SQL_SELECT, new AllLocationMapper(), locationId);
    }

    @Override
    public List<Location> heroByLocation(String name){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        java.util.Date newDate = sdf.parse(date);
//        java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());

        return jdbcTemplate.query(SQL_HEROBYLOCATION, new LocationMapper(), name);
    }
    
    @Override
    public List<Location> locationByHero(String name){
        return jdbcTemplate.query(SQL_LOCATIONSBYHERO, new LocationMapper(), name); 
    }
    
    @Override
    public List <Location> locationHeroByDate (String date) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date newDate = sdf.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(newDate.getTime());
        return jdbcTemplate.query(SQL_LOCATIONHEROBYDATE, new LocationMapper(), date);
    }

    private static final class AllLocationMapper implements RowMapper<Location> {

        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("location_id"));
            location.setLocationName(rs.getString("location_name"));
            location.setLocationDesc(rs.getString("location_description"));
            location.setLat(rs.getDouble("lat"));
            location.setLongitude(rs.getDouble("longitue"));
            location.setState(rs.getString("state"));
            location.setStreet(rs.getString("street"));
            location.setCity(rs.getString("city"));
            return location;
        }
    }

    private static final class LocationMapper implements RowMapper<Location> {

        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("location_id"));
            location.setLocationName(rs.getString("location_name"));
            location.setLocationDesc(rs.getString("location_description"));
            location.setLat(rs.getDouble("lat"));
            location.setLongitude(rs.getDouble("longitue"));
            location.setState(rs.getString("state"));
            location.setStreet(rs.getString("street"));
            location.setCity(rs.getString("city"));

            Integer heroCheck = rs.getInt("hero_id");

            if (heroCheck != null) {
                Hero hero = new Hero();
                hero.setHeroId(rs.getInt("hero_id"));
                hero.setHeroName(rs.getString("hero_name"));
                location.setHero(hero);
            }

            Integer sightCheck = rs.getInt("sighting_id");

            if (sightCheck != null) {
                Sighting sighting = new Sighting();
                sighting.setSightingDate(rs.getDate("sightdate").toLocalDate());
                sighting.setSightingId(rs.getInt("sighting_id"));
                location.setSighting(sighting);
            }

            return location;
        }
    }
}
