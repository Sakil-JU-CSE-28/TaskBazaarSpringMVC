package dao;

import lombok.AllArgsConstructor;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RoleDao roleDao;

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, userRowMapper());
            if (user != null) {
                user.setRoles(roleDao.findRolesByUserId(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public User save(User user) {
        String sql = "INSERT INTO users (username, password, email, full_name, enabled) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setBoolean(5, user.isEnabled());
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        user.getRoles().forEach(role -> {
            String query = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(query, user.getId(), role.getId());
        });
        return user;
    }

    public void update(User user) {
        String sql = "UPDATE users SET password = ?, email = ?, full_name = ?, enabled = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getEmail(), user.getFullName(), user.isEnabled(), user.getId());
        sql = "DELETE FROM user_roles WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getId());
        user.getRoles().forEach(role -> {
            String query = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(query, user.getId(), role.getId());
        });
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, userRowMapper());
        users.forEach(user -> user.setRoles(roleDao.findRolesByUserId(user.getId())));
        return users;
    }

    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, userRowMapper());
            if (user != null) {
                user.setRoles(roleDao.findRolesByUserId(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public Integer getCntByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    public Integer getCntByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }

    public void updateEnabledStatus(Long userId, boolean enabled) {
        String sql = "UPDATE users SET enabled = ? WHERE id = ?";
        jdbcTemplate.update(sql, enabled, userId);
    }

    private RowMapper<User> userRowMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setEnabled(rs.getBoolean("enabled"));
                return user;
            }
        };
    }
}
