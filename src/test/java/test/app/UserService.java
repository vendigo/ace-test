package test.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserRowMapper rowMapper = new UserRowMapper();

    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT name, age FROM TUser", rowMapper);
    }

    public List<User> getOldUsers() {
        return jdbcTemplate.query("SELECT name, age FROM TOldUser", rowMapper);
    }

    public void insertOldUsers(List<User> users) {
        for (User user : users) {
            jdbcTemplate.update("INSERT INTO TOldUser (name, age) VALUES (?, ?)", preparedStatement -> {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setInt(2, user.getAge());
            });
        }

    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            return user;
        }
    }
}
