package com.fromscratch.users.application.authentication;

import com.fromscratch.users.domain.authentication.AuthenticationRequest;
import com.fromscratch.users.domain.authentication.AuthenticationService;

public class AuthenticationPage {

    private static final String ERROR_DIV = """
        <div>
            <span style='color:red'>Incorrect login or password</span>
        </div>
    """;

    private static final String FORM_HTML = """
        <form id='authenticationForm' action='processAuthentication'>
            <input type='text' id='userLogin' name='fUserLogin'>
            <input type='password' id='userPassword' name='fUserPassword'>
            <input type='submit' id='loginButton' name='fLoginButton' value='Login' />
        </form>
    """;

    private AuthenticationService authenticationService;

    public AuthenticationPage(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public String getAuthenticationForm() {
        return getAuthenticationForm("");
    }

    public String processAuthentication() {
        try {
            authenticationService.authenticate(new AuthenticationRequest("user", "password"));
        } catch (Exception e) {
            return getAuthenticationForm(ERROR_DIV);
        }
        return "redirect:/users";
    }

    private String getAuthenticationForm(String errorDiv) {
        return errorDiv + FORM_HTML;
    }

}
