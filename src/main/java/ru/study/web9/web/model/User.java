package ru.study.web9.web.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users", schema = "web6_db")
public class User {
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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id",
                            referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id",
                            referencedColumnName = "id")
            })
    List<Role> roles = new ArrayList<Role>();


    @Column(name = "rating")
    @ColumnDefault("0")
    private Long rating;

    public User() {
    }

    public User(long id, String login, String email, String password, List<Role> roles, Long rating) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.rating = rating;
    }

    public User(String name, String email, String password) {
        this.login = name;
        this.email = email;
        this.password = password;
    }

    public User(String login, String email, String password, List<Role> roles, Long rating) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.rating = rating;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", login='" + this.login + '\'' +
                ", email='" + this.email + '\'' +
                ", password='" + this.password + '\'' +
                ", role='" + this.roles.toString() + '\'' +
                ", rating=" + this.rating +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
