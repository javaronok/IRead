package com.iread.security;

import com.iread.model.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class AuthorityService {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationToken authenticate(String userName, String password) {
        List<User> users = userService.findByUserNameAndEnabled(userName, true);

        for (User user : users) {
            boolean matches = false;
            try {
                matches = passwordEncoder.matches(password, user.getPassword());
            } catch (Exception e) {}

            if (matches) {
                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
            }
        }
        return null;
    }
}

