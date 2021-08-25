package com.persholas.security;

import com.persholas.models.AuthGroup;
import com.persholas.models.Customer;
import com.persholas.models.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AppUserPrincipal implements UserDetails {

    private Employee employee;
    private Customer customer;
    private List<AuthGroup> authGroups;

    //auth for employee
    public AppUserPrincipal(Employee employee, List<AuthGroup> authGroups)
    {
        this.employee = employee;
        this.authGroups = authGroups;
    }

    //auth for customer
    public AppUserPrincipal(Customer customer, List<AuthGroup> authGroups)
    {
        this.customer = customer;
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
        if(this.employee != null){
            return this.employee.getEPassword();
        }
        return  this.customer.getCPassword();
    }

    @Override
    public String getUsername() {
        if(this.employee != null){
            return this.employee.getEUsername();
        }
        return  this.customer.getCUsername();
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
}
