package service;

import dao.RoleDao;
import lombok.AllArgsConstructor;
import model.Role;
import model.User;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    public List<Role> getAll() {
        return roleDao.findAll();
    }

    public boolean hasRole(User user, String roleName) {
        Set<Role> roles = roleDao.findRolesByUserId(user.getId());
        Long roleId = roleDao.findRoleIdByName(roleName);
        return roles.contains(new Role(roleId, roleName));
    }

}
