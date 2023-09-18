package com.fromscratch.users.application.authentication;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fromscratch.users.application.authentication.AuthenticationPage;
import com.fromscratch.users.domain.authentication.AuthenticationService;
import com.fromscratch.users.domain.authentication.IAuthenticationRepository;

@DisplayName("Authentication Page")
class AuthenticationPageTest {

    private final static String EXPECTED_DEFAULT_FORM_HTML = """
        <form id='authenticationForm' action='processAuthentication'>
            <input type='text' id='userLogin' name='fUserLogin'>
            <input type='password' id='userPassword' name='fUserPassword'>
            <input type='submit' id='loginButton' name='fLoginButton' value='Login' />
        </form>
    """;

    private final static String EXPECTED_DEFAULT_FORM_HTML_WITH_ERROR_DIV = """
        <div>
            <span style='color:red'>Incorrect login or password</span>
        </div>
        <form id='authenticationForm' action='processAuthentication'>
            <input type='text' id='userLogin' name='fUserLogin'>
            <input type='password' id='userPassword' name='fUserPassword'>
            <input type='submit' id='loginButton' name='fLoginButton' value='Login' />
        </form>
    """;

    IAuthenticationRepository authenticationRepository;
    AuthenticationService authenticationService;
    AuthenticationPage authenticationPage;

    @BeforeEach
    void setUp() {
        authenticationRepository = mock(IAuthenticationRepository.class);
        authenticationService = new AuthenticationService(authenticationRepository);
        authenticationPage = new AuthenticationPage(authenticationService);
    }

    @Test 
    @DisplayName("should present authentication form as the default page")
    void shouldPresentAnAuthenticationFormToTheUser(){
        String authenticationForm = authenticationPage.getAuthenticationForm();

        assertThat(authenticationForm.trim()).isEqualTo(EXPECTED_DEFAULT_FORM_HTML.trim());
    }

    @Test 
    @DisplayName("should present an error message right above authentication form when the user login or password are incorrect")
    void shouldPresentAnErrorMessageRightAboveAuthenticationFormWhenTheUserLoginOrPasswordAreIncorrect(){
        String authenticationFormWithError = authenticationPage.processAuthentication();

        assertThat(authenticationFormWithError.trim()).isEqualTo(EXPECTED_DEFAULT_FORM_HTML_WITH_ERROR_DIV.trim());
    }

    @Test 
    @DisplayName("should return redirection to users page if authentication goes well")
    void shouldReturnRedirectionToUsersPageIfAuthenticationGoesWell(){
        when(authenticationRepository.findByLoginAndPassword("user", "password")).thenReturn(Optional.of("dummyToken"));

        String authenticationSucceed = authenticationPage.processAuthentication();

        assertThat(authenticationSucceed).isEqualTo("redirect:/users");
    }

}
