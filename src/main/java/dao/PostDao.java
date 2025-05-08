package dao;

import lombok.AllArgsConstructor;
import model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class PostDao {

    private final JdbcTemplate jdbcTemplate;

    public void save(Post post) {
        String sql = "INSERT INTO posts (title, content,author) VALUES (?, ?,?)";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getAuthor());
    }

    public void update(Post post) {
        String sql = "UPDATE posts SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());
    }

    public Post getById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new PostRowMapper(), id);
    }

    public List<Post> getAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setAuthor(rs.getString("author"));
            return post;
        }
    }
}