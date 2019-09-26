package ru.study.web9.web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity(name = "User")
@Table(name = "users", schema = "web6_db")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "login")
    private String login;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id",
                            referencedColumnName = "id")
            })
    Collection<Role> roles = new ArrayList<Role>();


    public User() {
    }

    public User(long id, String login, String email, String password, List<Role> roles) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(long id, String login, String email, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.login = name;
        this.email = email;
        this.password = password;
    }

    public User(String login, String email, String password, List<Role> roles) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.roles = (Collection<Role>) authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", login='" + this.login + '\'' +
                ", email='" + this.email + '\'' +
                ", password='" + this.password + '\'' +
                ", role='" + this.roles.toString() + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


}
