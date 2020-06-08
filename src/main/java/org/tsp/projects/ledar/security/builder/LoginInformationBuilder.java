package org.tsp.projects.ledar.security.builder;

import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.Person;
import org.tsp.projects.ledar.security.payload.response.AuthRolesResponse;
import org.tsp.projects.ledar.security.payload.response.LoginInformationResponse;
import org.tsp.projects.ledar.security.payload.response.PersonResponse;

import java.util.List;
import java.util.stream.Collectors;

public class LoginInformationBuilder {

    public static LoginInformationResponse constructLoginInformationLoad(LoginInformation loginInfo, Boolean isDetail) {

        LoginInformationResponse loginInformationLoad = null;
        PersonResponse personPayload = null;

        if (loginInfo != null) {
            if (!isDetail) {
                Person person = loginInfo.getLoginPerson();
                if (person != null && person.getFullName() != null) {
                    personPayload = PersonBuilder.buildPersonPayload(person, false);
                }

                List<AuthRolesResponse> userRoles = loginInfo.getUserRoleList().stream().map(role ->
                        new AuthRolesResponse(role.getAuthenticationRoleId().getId(), role.getAuthenticationRoleId().getRoleName(),
                        role.getAuthenticationRoleId().getCreated(), role.getAuthenticationRoleId().getModified())).collect(Collectors.toList());

                loginInformationLoad = new LoginInformationResponse(loginInfo.getId(), loginInfo.getUsername(), loginInfo.isActive(),
                        personPayload, userRoles, loginInfo.getPwordResetRequired(), loginInfo.getCreated(),
                        loginInfo.getModified());
            } else {
                loginInformationLoad = constructLoginInformationLoad(loginInfo, false);

            }
        }

        return loginInformationLoad;
    }
}
