package com.fromscratch.users.domain.authorization;

public class AuthorizationService {

    private final IAuthorizationRepository roleRepository;

    public AuthorizationService(IAuthorizationRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean authorize(AuthorizationRequest request) {
        if (!roleRepository.findByTokenAndRole(request.token(), request.role())) {
            throw new AuthorizationException();
        }
        return true;
    }

}
