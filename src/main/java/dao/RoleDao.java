package dao;

import lombok.AllArgsConstructor;
import model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RoleDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Role> findByName(String name) {
        try {
            String sql = "SELECT * FROM roles WHERE name = ?";
            Role role = jdbcTemplate.queryForObject(sql, new Object[]{name}, roleRowMapper());
            return Optional.ofNullable(role);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Role save(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO roles (name) VALUES (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getName());
            return ps;
        }, keyHolder);
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    public Set<Role> findRolesByUserId(Long userId) {
        String sql = "SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?";
        List<Role> roles = jdbcTemplate.query(sql, new Object[]{userId}, roleRowMapper());
        return new HashSet<>(roles);
    }

    public Long findRoleIdByName(String roleName) {
        String sql = "SELECT id FROM roles WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, roleName);
    }

    public List<Role> findAll() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, roleRowMapper());
    }

    private RowMapper<Role> roleRowMapper() {
        return new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                return role;
            }
        };
    }

}
