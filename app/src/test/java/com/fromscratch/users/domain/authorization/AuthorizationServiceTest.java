package com.fromscratch.users.domain.authorization;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Authorization Service")
class AuthorizationServiceTest {

    IAuthorizationRepository roleRepository;
    AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        roleRepository = mock(IAuthorizationRepository.class);
        when(roleRepository.findByTokenAndRole("dummyToken", "list-users")).thenReturn(true);
        when(roleRepository.findByTokenAndRole(eq("dummyToken"), not(eq("list-users")))).thenReturn(false);
        when(roleRepository.findByTokenAndRole(not(eq("dummyToken")), eq("list-users"))).thenReturn(false);
        when(roleRepository.findByTokenAndRole(not(eq("dummyToken")), not(eq("list-users")))).thenReturn(false);
        
        authorizationService = new AuthorizationService(roleRepository);
    }

    @Test
    @DisplayName("should authorize a user")
    void shouldAuthorizeAUser() {
        AuthorizationRequest request = new AuthorizationRequest("dummyToken", "list-users");

        boolean authorized = authorizationService.authorize(request);

        assertThat(authorized).isTrue();
    }

    @Test
    @DisplayName("should not authorize a user when login or password are incorrect")
    void shouldNotAuthenticateAUserWhenLoginOrPasswordAreIncorrect() {
        AuthorizationRequest wrongTokenRequest = new AuthorizationRequest( "dummyToken2", "list-users");
        AuthorizationRequest wrongRoleRequest = new AuthorizationRequest( "dummyToken", "list-roles");
        AuthorizationRequest wrongTokenAndRoleRequest = new AuthorizationRequest( "dummyToken2", "list-roles");

        assertThatThrownBy(() -> authorizationService.authorize(wrongTokenRequest))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("Either token is not valid or teh user does not have permission to perform this action");
        assertThatThrownBy(() -> authorizationService.authorize(wrongRoleRequest))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("Either token is not valid or teh user does not have permission to perform this action");
        assertThatThrownBy(() -> authorizationService.authorize(wrongTokenAndRoleRequest))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("Either token is not valid or teh user does not have permission to perform this action");
    }
    
}
