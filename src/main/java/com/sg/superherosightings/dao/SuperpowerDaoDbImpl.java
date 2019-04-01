/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Hero;
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
public class SuperpowerDaoDbImpl implements SuperpowerDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_ADD
            = "insert into superpower (superpower_name, superpower_description) "
            + "values (?, ?)";
    private static final String SQL_UPDATE
            = "update superpower set superpower_name = ?, superpower_description = ? where superpower_id = ? ";
    private static final String SQL_DELETE
            = "delete from superpower where superpower_id = ? ";
    private static final String SQL_LISTALLPOWERS
            = "select * from superpower";
    private static final String SQL_SELECTPOWER
            = "select * from superpower where superpower_name = ?";
    
    @Override
    public Superpower selectPower(String name) {
        return jdbcTemplate.queryForObject(SQL_SELECTPOWER, new PowerMapper(), name);
    }

    @Override
    public List<Superpower> listAllPowers() {
        return jdbcTemplate.query(SQL_LISTALLPOWERS, new PowerMapper());
    }

    @Override
    public void updatePower(Superpower power) {
        jdbcTemplate.update(SQL_UPDATE, power.getSuperpowerName(), power.getSuperpowerDesc(), power.getSuperpowerId());
    }

    @Override
    public void deletePower(int superpowerId) {
        jdbcTemplate.update(SQL_DELETE, superpowerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Superpower addPower(Superpower power) {
            jdbcTemplate.update(SQL_ADD,
                power.getSuperpowerName(), power.getSuperpowerDesc());
                
        // query the database for the id that was just assigned
        int newId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class);
        // set the new id value on the object and return it
        power.setSuperpowerId(newId);
        return power;
    }

    private static final class PowerMapper implements RowMapper<Superpower> {

        public Superpower mapRow(ResultSet rs, int rowNum) throws SQLException {
            Superpower power = new Superpower();
            power.setSuperpowerId(rs.getInt("superpower_id"));
            power.setSuperpowerName(rs.getString("superpower_name"));
            power.setSuperpowerDesc(rs.getString("superpower_description"));
            return power;
        }
    }

}
