package com.hedstrom.hellosaml.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements SAMLUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public Object loadUserBySAML(SAMLCredential credential)
    throws UsernameNotFoundException {

        String userID = credential.getNameID().getValue();

        LOG.info(userID + " was logged in");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //Add role/authority level
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);

        //locate user in database
        //returns 
            // username
            // password
            // enabled: set to true if the user is enabled
            // accountNonExpired: set to true if the account has not expired
            // credentialsNonExpired: set to true if the credentials have not expired
            // accountNonLocked: set to true if the account is not locked
            // authorities: the authorities that should be granted to the caller if they presented the correct username and password and the user is enabled. Not null.
        
        return new User(userID, "<testPassword>", true, true, true, true, authorities);
    }


}