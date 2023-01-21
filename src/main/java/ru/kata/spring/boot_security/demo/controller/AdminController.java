package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/users";
    }
    @GetMapping("/new")
    public String saveNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin/new";
    }
    @PostMapping("/saveNewUser")
    public String saveUser(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/{id}/edit")
    public String update(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "admin/edit";
    }
    @PatchMapping("/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") User user){
        userService.edit(id, user);
        return "redirect:/admin/users";
    }
    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
