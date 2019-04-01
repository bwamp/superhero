/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Hero;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Superpower;
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
public class HeroDaoDbImpl implements HeroDao {

    private static final String SQL_SELECTHERO
            = "select a.*, b.superpower_name, b.superpower_description "
            + "from hero as a "
            + "inner join superpower as b on b.superpower_id = a.superpower_id "
            + "where a.hero_id = ?";
    private static final String SQL_SELECTALLHERO
            = "select a.*, b.superpower_name, b.superpower_description "
            + "from hero as a "
            + "inner join superpower as b on b.superpower_id = a.superpower_id ";
    private static final String SQL_ADD
            = "insert into hero (hero_name, hero_description, superpower_id) "
            + "values (?, ?, ?)";
    private static final String SQL_UPDATE
            = "update hero set hero_name = ?, hero_description = ?, superpower_id = ? where hero_id = ? ";
    private static final String SQL_DELETE
            = "delete from hero where hero_id = ? ";
    private static final String SQL_NEWHEROSIGHTING
            = "insert into hero_sighting (hero_id, sighting_id) values (?, ?) ";
    private static final String SQL_NEWHEROORG
            = "insert into hero_org (herofk_id, organization_id) values (?, ?) ";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hero selectHero(int heroId) {
        return jdbcTemplate.queryForObject(SQL_SELECTHERO, new HeroMapper(), heroId);
    }

    @Override
    public List<Hero> listAllHeroes() {
        return jdbcTemplate.query(SQL_SELECTALLHERO, new HeroMapper());
    }

    @Override
    public void updateHero(Hero hero, Superpower superpower) {
        jdbcTemplate.update(SQL_UPDATE, hero.getHeroName(), hero.getHeroDesc(), superpower.getSuperpowerId(), hero.getHeroId());
    }

    @Override
    public void deleteHero(int heroId) {
        jdbcTemplate.update(SQL_DELETE, heroId);
    }
    
    @Override
    public void newHeroSighting (Hero hero, int sightNumber){
        jdbcTemplate.update(SQL_NEWHEROSIGHTING, hero.getHeroId(), sightNumber);
    }
    
    @Override
    public void newHeroOrg (Hero hero, int orgNumber){
        jdbcTemplate.update(SQL_NEWHEROORG, hero.getHeroId(), orgNumber);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero addHero(Hero hero, Superpower superpower) {
        jdbcTemplate.update(SQL_ADD,
                hero.getHeroName(),
                hero.getHeroDesc(),
                superpower.getSuperpowerId());
                
        // query the database for the id that was just assigned
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the object and return it
        hero.setHeroId(newId);
        return hero;
    }

    private static final class HeroMapper implements RowMapper<Hero> {

        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroId(rs.getInt("hero_id"));
            hero.setHeroName(rs.getString("hero_name"));
            hero.setHeroDesc(rs.getString("hero_description"));

            Superpower superpower = new Superpower();
            superpower.setSuperpowerId(rs.getInt("superpower_id"));
            superpower.setSuperpowerName(rs.getString("superpower_name"));
            superpower.setSuperpowerDesc(rs.getString("superpower_description"));

            hero.setSuperpower(superpower);
            return hero;
        }
    }
}
