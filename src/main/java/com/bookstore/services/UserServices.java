package com.bookstore.services;

import com.bookstore.entity.Role;
import com.bookstore.entity.User;
import com.bookstore.repository.IRoleRepository;
import com.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUserName(user.getName());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (userId != 0 && roleId != 0)
            userRepository.addRoleToUser(userId, roleId);
    }

    public void addRoleForUser(Long userID, Long roleID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleID).orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoleOfUser(Long userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user != null) {
            return user.getRoles();
        }
        return null;
    }

    public User getById(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        userRepository.save(user);
    }
}
