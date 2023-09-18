package com.fromscratch.users.domain.authorization;

public interface IAuthorizationRepository {

    boolean findByTokenAndRole(String token, String role);

}
