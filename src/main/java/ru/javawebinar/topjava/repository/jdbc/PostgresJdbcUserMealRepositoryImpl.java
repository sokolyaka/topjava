package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;

@Repository
@Profile(Profiles.POSTGRES)
public class PostgresJdbcUserMealRepositoryImpl extends JdbcUserMealRepository {

    @Autowired
    public PostgresJdbcUserMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
