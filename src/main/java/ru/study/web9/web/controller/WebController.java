package ru.study.web9.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.study.web9.web.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {
    @Autowired
    private UserService userService;

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
        return "admin";
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List roles = (auth.getAuthorities().stream().map(o -> ((GrantedAuthority) o).getAuthority().substring(5)).collect(Collectors.toList()));
        System.out.println(roles);
        if (auth.getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("roles", roles);
            return "admin";

        } else if (auth.getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_USER"))) {
            model.addAttribute("roles", roles);
            return "user";
        }
        return "index";

    }

}
