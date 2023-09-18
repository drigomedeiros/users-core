package com.fromscratch.users.application.users;

import java.util.List;
import java.util.logging.Logger;

import com.fromscratch.users.domain.authorization.AuthorizationRequest;
import com.fromscratch.users.domain.authorization.AuthorizationService;
import com.fromscratch.users.domain.users.ListUsersService;
import com.fromscratch.users.domain.users.User;

public class ListUsersPage {

    private static final String USERS_LIST_TEMPLATE = """
        <div>
            <ul>
                #LIST_ITEMS#
            </ul>
        </div>
    """;

    private static final String USERS_LIST_ITEM = """
                <li>#USER_LOGIN# - #USER_NAME#</li>
    """;

    private static final String NO_USERS_MESSAGE = """
        <div>
            <p>There is no users registered.</p>
        </div>
    """;

    private ListUsersService listUsersService;
    private AuthorizationService authorizationService;

    public ListUsersPage(ListUsersService listUsersService, AuthorizationService authorizationService) {
        this.listUsersService = listUsersService;
        this.authorizationService = authorizationService;
    }

    public String listUsers(String authenticationToken) {
        try {
            authorizationService.authorize(new AuthorizationRequest(authenticationToken, "users-list"));

            var users = listUsersService.list();
            if(users.isEmpty()) {
                return NO_USERS_MESSAGE;
            }
            return buildUsersList(users);
        } catch (Exception e) {
            Logger.getLogger(ListUsersPage.class.getName()).warning(e.getMessage());
            return "redirect:/authentication";
        }
        
    }

    private String buildUsersList(List<User> users) {
        var userListItems = new StringBuilder();
        users.forEach(user -> {
            var userListItem = USERS_LIST_ITEM
                                    .replace("#USER_LOGIN#", user.userLogin())
                                    .replace("#USER_NAME#", user.userName())
                                    .trim();
            userListItems.append(userListItem);
        });
        return USERS_LIST_TEMPLATE.replace("#LIST_ITEMS#", userListItems.toString());
    }

}
