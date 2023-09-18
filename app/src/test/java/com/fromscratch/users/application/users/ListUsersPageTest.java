package com.fromscratch.users.application.users;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static java.util.Collections.emptyList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fromscratch.users.domain.authorization.AuthorizationService;
import com.fromscratch.users.domain.authorization.IAuthorizationRepository;
import com.fromscratch.users.domain.users.IUserRepository;
import com.fromscratch.users.domain.users.ListUsersService;
import com.fromscratch.users.domain.users.User;

@DisplayName("Users List Page")
class ListUsersPageTest {

    private static final String EXPECTED_USERS_LIST = """
        <div>
            <ul>
                <li>userLogin1 - userName1</li><li>userLogin2 - userName2</li><li>userLogin3 - userName3</li>
            </ul>
        </div>
    """;

    private static final String EXPECTED_NO_USERS_MESSAGE = """
        <div>
            <p>There is no users registered.</p>
        </div>
    """;

    IAuthorizationRepository authorizationRepository;
    AuthorizationService authorizationService;
    IUserRepository userRepository;
    ListUsersService listUsersService;
    ListUsersPage listUsersPage;

    @BeforeEach
    void setup(){      
        authorizationRepository = mock(IAuthorizationRepository.class);
        authorizationService = new AuthorizationService(authorizationRepository);
        userRepository = mock(IUserRepository.class);
        listUsersService = new ListUsersService(userRepository);
        listUsersPage = new ListUsersPage(listUsersService, authorizationService);

        when(authorizationRepository.findByTokenAndRole("dummyToken", "users-list")).thenReturn(true);
    }

    @Test
    @DisplayName("should present a list of users to the user")
    void shouldPresentAListOfUsersToTheUser(){
        when(userRepository.findAll()).thenReturn(List.of(
            new User("userLogin1", "userName1"),
            new User("userLogin2", "userName2"),
            new User("userLogin3", "userName3")
        ));

        String usersList = listUsersPage.listUsers("dummyToken");

        assertThat(usersList.trim()).isEqualTo(EXPECTED_USERS_LIST.trim());
    }

    @Test
    @DisplayName("should present a message when there is no users registered")
    void shouldPresentAMessageWhenThereIsNoUsersRegistered(){
        when(userRepository.findAll()).thenReturn(emptyList());

        String usersList = listUsersPage.listUsers("dummyToken");

        assertThat(usersList.trim()).isEqualTo(EXPECTED_NO_USERS_MESSAGE.trim());
    }

    @Test
    @DisplayName("should redirect to authentication page when the user is not authenticated/authorized")
    void shouldRedirectToAuthenticationPageWhenUserIsNotAuthenticatedOrAuthorized(){
        String authenticationPageRedirect = listUsersPage.listUsers("wrongDummyToken");

        assertThat(authenticationPageRedirect).isEqualTo("redirect:/authentication");
        verify(userRepository, never()).findAll();
    }
    
}
