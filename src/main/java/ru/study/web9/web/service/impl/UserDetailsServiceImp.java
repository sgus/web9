package ru.study.web9.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.web9.web.dao.UserDAO;
import ru.study.web9.web.model.Role;
import ru.study.web9.web.model.User;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private UserDAO userDetailsDao;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User userByUsername = userDetailsDao.findUserByUsername(username);
    return userByUsername;
  }
}