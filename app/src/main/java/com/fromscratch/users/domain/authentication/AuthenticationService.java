package com.fromscratch.users.domain.authentication;

public class AuthenticationService {

    private final IAuthenticationRepository userRepository;

    public AuthenticationService(IAuthenticationRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(AuthenticationRequest request) {
        var userToken = userRepository.findByLoginAndPassword(request.userLogin(), request.userPassword());
        return userToken.orElseThrow(() -> new AuthenticationException("Login or password are incorrect"));
    }

}
