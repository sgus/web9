package ru.study.web9.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.study.web9.web.model.Role;
import ru.study.web9.web.model.User;
import ru.study.web9.web.service.RoleService;
import ru.study.web9.web.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List roles = (auth.getAuthorities().stream().map(o -> ((GrantedAuthority) o).getAuthority().substring(5)).collect(Collectors.toList()));
        model.addAttribute("roles", roles);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List roles = (auth.getAuthorities().stream().map(o -> ((GrantedAuthority) o).getAuthority().substring(5)).collect(Collectors.toList()));
        model.addAttribute("roles", roles);
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        model.addAttribute("userT", new User());


        model.addAttribute("roleList", roleService.getAllRoles());

        return "admin";
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List roles = (auth.getAuthorities().stream().map(o -> ((GrantedAuthority) o).getAuthority().substring(5)).collect(Collectors.toList()));
        if (auth.getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("roles", roles);
            return "redirect:/admin";

        } else if (auth.getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user";
        }
        return "index";

    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(name = "id", required = false) Long id) {
        System.out.println("=========================");
        System.out.println(id);
        userService.deleteById(id);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute("user") User user,
                       @RequestParam(value = "rols", required = false) int[] rols,
                       BindingResult bindingResult, Model model) {
        if (rols != null) {
            Role role = null;
            for (int i = 0; i < rols.length; i++) {

                role = roleService.getRoleById(rols[i]);
                role.setId(rols[i]);
                user.getRoles().add(role);
            }

        }

        userService.updateUser(user);

        return "redirect:/admin";

    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rols", required = false) int[] rols,
                         BindingResult bindingResult, Model model) {
        if (rols != null) {
            Role role = null;
            for (int i = 0; i < rols.length; i++) {

                role = roleService.getRoleById(rols[i]);
                role.setId(rols[i]);
                user.getRoles().add(role);
            }

        }

        System.out.println("********************************");
        System.out.println(user.toString());
        System.out.println("********************************");
        userService.addUser(user);

        return "redirect:/admin";

    }
}