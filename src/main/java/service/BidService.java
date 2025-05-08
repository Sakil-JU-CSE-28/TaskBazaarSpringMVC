package service;

import dao.BidDao;
import dao.UserDao;
import dto.BidDto;
import lombok.AllArgsConstructor;
import model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import util.Constants;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BidService {

    private final BidDao bidDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public void save(Long postId, String bidder) {
        Optional<User> user = userDao.findByUsername(bidder);
        if (user.isEmpty()) {
            throw new RuntimeException(Constants.Error.USER_NOT_FOUND);
        }
        bidDao.save(postId, user.get().getId());
    }

    public void accept(Long bidId) {
        bidDao.updateAcceptedStatus(bidId, true);
    }


    public List<BidDto> getAllByPostId(Long postId) {
        return bidDao.getAllByPostId(postId).stream().map(
                bid -> modelMapper.map(bid, BidDto.class)).collect(Collectors.toList()
        );
    }
}
