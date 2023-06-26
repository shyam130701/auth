package com.authservice.auth.config;

import com.authservice.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ApplicationConfig applicationConfig;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
        applicationConfig = new ApplicationConfig(userRepository);
    }


    @Test
    public void testAuthenticationManager() throws Exception {
        // Arrange
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        // Act
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(authenticationConfiguration);

        // Assert
        assertNotNull(authenticationManager);
    }

    @Test
    public void testPasswordEncoder() {
        // Act
        PasswordEncoder encoder = applicationConfig.passwordEncoder();

        // Assert
        assertNotNull(encoder);
        assertEquals(BCryptPasswordEncoder.class, encoder.getClass());
    }


}