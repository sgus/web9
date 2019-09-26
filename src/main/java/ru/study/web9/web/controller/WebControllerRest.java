package ru.study.web9.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List roles = (auth.getAuthorities().stream().map(o -> ((GrantedAuthority) o).getAuthority().substring(5)).collect(Collectors.toList()));
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @GetMapping("/roles")
    public List<Role> listRole() {
        return roleService.getAllRoles();
    }

    @PostMapping(value = "/create", produces = "application/json")
    public User createUser(@Valid @RequestBody User user) {
        List<Integer> rols = user.getRoles().stream().map(role -> Integer.parseInt(role.getName())).collect(Collectors.toList());
        user.getRoles().clear();
        if (rols != null) {
            Role role = null;
            for (int rol : rols) {
                role = roleService.getRoleById(rol);
                role.setId(rol);
                user.getRoles().add(role);
            }

        }
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
        userById.getRoles().clear();

        List<Integer> rols = user.getRoles().stream().map(role -> Integer.parseInt(role.getName())).collect(Collectors.toList());

        if (rols != null) {
            Role role = null;
            for (int rol : rols) {
                role = roleService.getRoleById(rol);
                role.setId(rol);
                userById.getRoles().add(role);
            }

        }
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
