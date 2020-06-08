package org.tsp.projects.ledar.security.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tsp.projects.ledar.security.auth.JwtTokenProvider;
import org.tsp.projects.ledar.security.auth.UserPrincipal;
import org.tsp.projects.ledar.security.builder.LoginInformationBuilder;
import org.tsp.projects.ledar.security.enums.ApplicationActivitiesEnum;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.UsersLastActivities;
import org.tsp.projects.ledar.security.payload.request.LoginInformationPayload;
import org.tsp.projects.ledar.security.payload.response.ApiResponse;
import org.tsp.projects.ledar.security.payload.response.JwtAuthenticationResponse;
import org.tsp.projects.ledar.security.repository.LoginInformationRepository;
import org.tsp.projects.ledar.security.repository.UsersLastActivitiesRepository;
import org.tsp.projects.ledar.security.util.ApplicationUtility;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/authenticationResource")
public class AuthenticationResource {

    private final AuthenticationManager authenticationManager;
    private final LoginInformationRepository loginInformationRepository;
    private final JwtTokenProvider tokenProvider;
    private final UsersLastActivitiesRepository usersLastActivitiesRepository;

    @Autowired
    public AuthenticationResource(AuthenticationManager authenticationManager, LoginInformationRepository loginRepository,
                                  JwtTokenProvider tokenProvider, UsersLastActivitiesRepository usersLastRepos) {
        this.authenticationManager = authenticationManager;
        this.loginInformationRepository = loginRepository;
        this.tokenProvider = tokenProvider;
        this.usersLastActivitiesRepository = usersLastRepos;
    }

    /**
     * This method is used to user authentication
     *
     * @param loginRequest - login json object
     * @return - ResponseEntity containing operation outcome and meta data of
     * user making the request
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginInformationPayload loginRequest) {

        log.info("{} trying to login from {} with IP {}", loginRequest.getUsername(), loginRequest.getLoginPortal(), loginRequest.getIpAddress());

        if (loginRequest.getLoginPortal().equals("MOBILE")) {
            //this should never happen
            if (loginRequest.getClient() == null || loginRequest.getLatitude() == null || loginRequest.getLongitude() == null) {
                return ResponseEntity.ok(new ApiResponse(false, "One or more required login fields is missing", null));
            }
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        //login is successful, we need to do business rule validation and save login here
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        LoginInformation loginInfo = loginInformationRepository.getOne(userPrincipal.getId());

        if (loginInfo.isActive()) {
            loginInfo.setLastLoginDate(new Date());
            loginInfo.setLastLoginIp(loginRequest.getIpAddress());
            loginInfo.setLatitude(loginRequest.getLatitude());
            loginInfo.setLongitude(loginRequest.getLongitude());
            loginInfo.setLoginStatus(Boolean.TRUE);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            JwtAuthenticationResponse holder = new JwtAuthenticationResponse(jwt);
            ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, holder.getAccessToken(),
                    LoginInformationBuilder.constructLoginInformationLoad(loginInfo, false));

            String message = loginInfo.getLoginPerson().getFullName() + " logged in successfully from " + loginRequest.getLoginPortal() + ": Client details are" + loginRequest.getClient()
                    + ", at latitude: " + loginRequest.getLatitude() + " and longitude: " + loginRequest.getLongitude() + ".";

            loginInfo.setDayKeyExpiry(ApplicationUtility.getThisTimeTomorrow());
            loginInformationRepository.save(loginInfo);
            usersLastActivitiesRepository.save(new UsersLastActivities(null, ApplicationActivitiesEnum.ACCOUNTLOGIN.toString(), new Date(), loginRequest.getIpAddress(), loginInfo.getId(),
                    "LoginInformation", loginRequest.getLongitude(), loginRequest.getLatitude(), loginRequest.getLoginPortal(), message, loginInfo, null, null));
            log.info("{} login action complete from IP: {} and Coordinates {} -- {}", loginRequest.getUsername(), loginRequest.getIpAddress(), loginRequest.getLongitude(),
                    loginRequest.getLatitude());
            return ResponseEntity.ok(apiResponse);
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Account disabled", null));
        }

    }
}
