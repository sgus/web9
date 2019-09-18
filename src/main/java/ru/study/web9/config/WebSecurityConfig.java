package ru.study.web9.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().antMatchers("/index").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/page").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/delete").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user").hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().usernameParameter("email").passwordParameter("password")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .loginPage("/index")
                .defaultSuccessUrl("/page")
                .loginProcessingUrl("/loginAction")
                .permitAll();

    }
}
