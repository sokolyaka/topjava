package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.UserMeal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcMapper implements RowMapper<UserMeal> {
    @Override
    public UserMeal mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMeal userMeal = new UserMeal();
        userMeal.setCalories(rs.getInt("calories"));
        userMeal.setDescription(rs.getString("description"));
        userMeal.setId(rs.getInt("id"));
        userMeal.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        return userMeal;
    }
}
