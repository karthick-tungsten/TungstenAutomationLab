package com.tungstenautomationlab.tungstenautomationlab.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum Roles {
    SUPERADMIN,ADMIN,MANAGER,USER;

    public Set<SimpleGrantedAuthority> getGrantedAuthority(){
        Set<SimpleGrantedAuthority> set=new HashSet<>();
        set.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return set;
    }
}
