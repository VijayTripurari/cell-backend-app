package com.cell.cellbackendapp;

import com.cell.cellbackendapp.entity.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CellDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private Object Integer;

    public Long getMaxCellForProviderKey(int providerKey) {

        String sql = "select max(tower_key) from sample.dbo.cell where provider_key=?";
     return  jdbcTemplate.queryForObject(sql, new Object[]{providerKey}, Long.class );
    }

}
