package ru.study.web9.web.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.study.web9.web.model.Role;
import ru.study.web9.web.model.User;
import ru.study.web9.web.service.RoleService;
import ru.study.web9.web.service.UserService;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class WebControllerRest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping(path = "/list", produces = "application/json")
    public List<User> list() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        User userById = userService.getUserById(Long.parseLong(id));
        System.out.println(userById.toString());
        return userById;
    }

    @GetMapping("/roles")
    public List<Role> listRole() {
        return roleService.getAllRoles();
    }

    @PostMapping(value = "/create", produces = "application/json")
    public User createUser(@RequestBody User user) {
        List<Role> roles = new ArrayList<>();
        if (user.getRoles() != null) {
            Role role = null;
            for (Role rol : user.getRoles()) {
                role = roleService.getRoleById(rol.getId());
                roles.add(role);
            }
        }
        user.setRoles(roles);
        userService.addUser(user);
        return user;
    }


    @PostMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Long userId, @RequestBody User user)
            throws ResourceNotFoundException {

        User userById = userService.getUserById(userId);
        userById.setEmail(user.getEmail());
        userById.setLogin(user.getLogin());
        userById.setPassword(user.getPassword());
        List<Role> roles = new ArrayList<>();
        if (user.getRoles() != null) {
            Role role = null;
            for (Role rol : user.getRoles()) {
                role = roleService.getRoleById(rol.getId());
                roles.add(role);
            }
        }
        userById.setRoles(roles);
        userService.updateUser(userById);

        return ResponseEntity.ok(userService.getUserById(userId));
    }


    @PostMapping("/delete/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        userService.deleteById(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
