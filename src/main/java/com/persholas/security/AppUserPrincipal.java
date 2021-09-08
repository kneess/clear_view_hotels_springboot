package com.persholas.security;

import com.persholas.models.AuthGroup;
import com.persholas.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AppUserPrincipal implements UserDetails {

    private User user;
    private List<AuthGroup> authGroups;

    public AppUserPrincipal(User user, List<AuthGroup> authGroups)
    {
        this.user = user;
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null == authGroups)
        {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(authGroup -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authGroup.getAAuthGroup()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getUPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }
    // method needed to get user first name on the front end
    public String getFirstName() { return this.user.getFirstName(); }
    // method needed to get user id on the front end
    public Long getUserId() { return this.user.getUserId(); }

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
}
