/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.fromscratch.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fromscratch.users.App;
import com.fromscratch.users.application.authentication.AuthenticationPage;

import static org.assertj.core.api.Assertions.*;

@DisplayName("App")
class AppTest {

    private final static String EXPECTED_DEFAULT_FORM_HTML = """
        <form id='authenticationForm' action='processAuthentication'>
            <input type='text' id='userLogin' name='fUserLogin'>
            <input type='password' id='userPassword' name='fUserPassword'>
            <input type='submit' id='loginButton' name='fLoginButton' value='Login' />
        </form>
    """;

    @Test 
    @DisplayName("should present authentication form as the default page")
    void shouldPresentAuthenticationFormAsTheDefaultPage() {
        App app = new App(new AuthenticationPage(null));
        assertThat(app.defaultPage().trim()).isEqualTo(EXPECTED_DEFAULT_FORM_HTML.trim());
    } 
    
}
