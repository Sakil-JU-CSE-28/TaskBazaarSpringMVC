package dao;

import lombok.AllArgsConstructor;
import model.Bid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
@AllArgsConstructor
public class BidDao {

    private final JdbcTemplate jdbcTemplate;

    public int countBidsByPostIdAndBidder(Long postId, Long bidder) {
        String sql = "SELECT COUNT(*) FROM bids WHERE post_id = ? AND bidder = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId, bidder);
    }

    public void save(Long postId, Long bidder) {
        String sql = "INSERT INTO bids (post_id, bidder) VALUES (?, ?)";
        jdbcTemplate.update(sql, postId, bidder);
    }

    public List<Bid> getAllByPostId(Long postId) {
        String sql = "SELECT * FROM bids WHERE post_id = ?";
        return jdbcTemplate.query(sql, bidRowMapper(), postId);
    }

    public void updateAcceptedStatus(Long bidId, boolean accepted) {
        String sql = "UPDATE bids SET is_accepted = ? WHERE id = ?";
        jdbcTemplate.update(sql, accepted, bidId);
    }

    private RowMapper<Bid> bidRowMapper() {
        return new RowMapper<Bid>() {
            @Override
            public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
                Bid bid = new Bid();
                bid.setId(rs.getLong("id"));
                bid.setPostId(rs.getLong("post_id"));
                bid.setBidder(rs.getLong("bidder"));
                bid.setAccepted(rs.getBoolean("is_accepted"));
                return bid;
            }
        };
    }

}
