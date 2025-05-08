package service;

import dao.RoleDao;
import dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Role;
import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import util.Constants;
import util.Utils;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public User save(User user, String roleName) {

        if (Utils.isNullOrEmpty(userDao.getCntByUsername(user.getUsername()).toString())) {
            log.error("User with username {} already exists", user.getUsername());
            throw new RuntimeException(Constants.Error.USER_ALREADY_EXISTS);
        }

        if (Utils.isNullOrEmpty(userDao.getCntByEmail(user.getEmail()).toString())) {
            log.error("User with email {} already exists", user.getEmail());
            throw new RuntimeException(Constants.Error.EMAIL_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleDao.findByName(roleName).orElseGet(() -> roleDao.save(new Role(roleName)));
        user.getRoles().add(role);
        return userDao.save(user);
    }
}
