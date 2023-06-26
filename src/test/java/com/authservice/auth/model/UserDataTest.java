package com.authservice.auth.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDataTest {

    private UserData userData;

    @BeforeEach
    public void setUp() {
        userData = UserData.builder()
                .id(1L)
                .name("John Doe")
                .age(30)
                .email("john.doe@example.com")
                .password("password")
                .role(Roles.ADMIN)
                .build();
    }

    @Test
    public void testGetters() {
        assertEquals(1L, userData.getId());
        assertEquals("John Doe", userData.getName());
        assertEquals(30, userData.getAge());
        assertEquals("john.doe@example.com", userData.getEmail());
        assertEquals("password", userData.getPassword());
        assertEquals(Roles.ADMIN, userData.getRole());
    }

    @Test
    public void testAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userData.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Roles.ADMIN.name())));
    }

    @Test
    public void testUsername() {
        assertEquals("john.doe@example.com", userData.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals("password", userData.getPassword());
    }

    @Test
    public void testAccountNonExpired() {
        assertTrue(userData.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        assertTrue(userData.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        assertTrue(userData.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled() {
        assertTrue(userData.isEnabled());
    }
}
