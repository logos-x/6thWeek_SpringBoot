package com.bookstore.controller;

import com.bookstore.entity.Role;
import com.bookstore.entity.User;
import com.bookstore.services.RoleService;
import com.bookstore.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserServices userServices;

    @GetMapping
    public String showAllUser(Model model) {
        model.addAttribute("accountList", userServices.getAll());
        return "role/list";
    }

    @GetMapping("/detail/{id}")
    public String showAllRoleOfUser(@PathVariable("id") Long userId, Model model) {
        User account = userServices.getById(userId);
        if (account != null) {
            model.addAttribute("account", account);
            List<Role> roles = userServices.getAllRoleOfUser(userId);
            if (roles != null) {
                model.addAttribute("roles", roles);
                return "role/detail";
            }
        }

         return "redirect:/roles";
    }

    @GetMapping("/add/{userId}")
    public String formAddRolesUser(@PathVariable("userId") Long userId, Model model) {
        User account = userServices.getById(userId);
        if (account != null) {
            model.addAttribute("account", account);
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "role/add";
        }
        return "role/detail";
    }

    @PostMapping("/add/{userId}")
    public String addRole(@PathVariable("userId") Long userId,
                          @RequestParam("roleId") Long roleId) {
        userServices.addRoleForUser(userId, roleId);
        return "redirect:/roles";
    }

    @GetMapping("/{userId}/delete/{roleId}")
    public String removeRole(@PathVariable("userId") Long userId,
                             @PathVariable("roleId") Long roleId) {
        userServices.removeRoleFromUser(userId, roleId);
        return "redirect:/roles";
    }
}
