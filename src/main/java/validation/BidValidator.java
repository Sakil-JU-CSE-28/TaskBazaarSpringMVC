package validation;

import dao.BidDao;
import dao.UserDao;
import enums.Role;
import lombok.AllArgsConstructor;
import model.User;
import org.springframework.stereotype.Component;
import service.RoleService;
import util.Constants;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class BidValidator {
    private final UserDao userDao;
    private final RoleService roleService;
    private final BidDao bidDao;

    public void validate(Long postId, String bidder) {
        List<String> errors = new ArrayList<>();
        if (Utils.isNullOrEmpty(bidder)) {
            errors.add(Constants.Error.BIDDER_EMPTY);
        }
        if (Utils.isNullOrEmpty(postId.toString())) {
            errors.add(Constants.Error.POST_ID_EMPTY);
        }

        Optional<User> user = userDao.findByUsername(bidder);
        if (user.isEmpty()) {
            errors.add(Constants.Error.USER_NOT_FOUND);
        }

        if (!roleService.hasRole(user.get(), Role.FREELANCER.toString())) {
            errors.add(Constants.Error.UNAUTHORIZED);
        }

        if (bidDao.countBidsByPostIdAndBidder(postId, user.get().getId()) > 0) {
            errors.add(Constants.Error.BID_ALREADY_ADDED);
        }
        if (!errors.isEmpty()) {
            throw new RuntimeException(errors.toString());
        }
    }
}