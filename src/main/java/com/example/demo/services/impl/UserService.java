package com.example.demo.services.impl;

import com.example.demo.dao.IUserRepo;
import com.example.demo.models.dto.ProfileInfo;
import com.example.demo.models.enteties.User;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * This class allows to work with user.
 *
 * @author Bogdan
 */
@Component
public class UserService implements IUserService {
    @Autowired
    private IUserRepo repo;

    /**
     * Returns an UserDetails object that can then be displayed to clients.
     *
     * @param username an username of user which is looking.
     * @return the user details at the specified URL
     * @see UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not founded.");
        }

        //SKIP authority functionality for demo
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(username, password,
                AuthorityUtils.NO_AUTHORITIES);
    }
}
