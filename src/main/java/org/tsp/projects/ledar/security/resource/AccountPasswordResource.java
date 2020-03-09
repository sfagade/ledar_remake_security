package org.tsp.projects.ledar.security.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tsp.projects.ledar.security.auth.UserPrincipal;
import org.tsp.projects.ledar.security.component.ExternalIntegrationComponent;
import org.tsp.projects.ledar.security.enums.ApplicationActivitiesEnum;
import org.tsp.projects.ledar.security.model.ContactInformation;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.Person;
import org.tsp.projects.ledar.security.payload.PayloadAbstractBase;
import org.tsp.projects.ledar.security.payload.response.ApiResponse;
import org.tsp.projects.ledar.security.repository.LoginInformationRepository;
import org.tsp.projects.ledar.security.service.LoggingService;
import org.tsp.projects.ledar.security.util.ApplicationUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/passwordManagement")
public class AccountPasswordResource {

    private final LoggingService loggingService;
    private final ExternalIntegrationComponent externalIntCom;
    private final PasswordEncoder passwordEncoder;
    private final LoginInformationRepository loginInfoRepos;

    @Autowired
    public AccountPasswordResource(LoggingService loggingService, ExternalIntegrationComponent externalIntCom,
                                   PasswordEncoder passwordEncoder, LoginInformationRepository loginInfoRepos) {
        this.passwordEncoder = passwordEncoder;
        this.externalIntCom = externalIntCom;
        this.loggingService = loggingService;
        this.loginInfoRepos = loginInfoRepos;
    }

    /**
     * This method is used by user's to change their password, this method will
     * not send any notification email when it's done
     *
     * @param criteriaRequest payload holding the new and old passwords
     * @return VdcnBasePayload
     */
    @RequestMapping("/changeMyPassword")
    public ResponseEntity<?> changeMyPassword(@RequestBody Map<String, Object> criteriaRequest) {

        PayloadAbstractBase basePayload = null;
        UserPrincipal currentPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginInformation currentUser = loginInfoRepos.getOne(currentPrincipal.getId());
        log.info("{} changing own's password", currentUser.getUsername());

        if (criteriaRequest != null) {
            String newPassword = criteriaRequest.get("newPassword").toString();

            if (newPassword != null && !newPassword.isEmpty() && newPassword.length() > 6) {
                if (passwordEncoder.matches(criteriaRequest.get("currentPassword").toString(), currentUser.getPassword())) {
                    currentUser.setPassword(passwordEncoder.encode(criteriaRequest.get("newPassword").toString()));
                    currentUser.setPwordResetRequired(false);
                    loginInfoRepos.save(currentUser);
                    final DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    criteriaRequest.put("message", currentUser.getUsername() + " changed own's password successfully today");
                    criteriaRequest.put("activityTime", dateTimeFormatter.format(new Date()));
                    criteriaRequest.put("activity", ApplicationActivitiesEnum.MYPASSWORD.toString());
                    criteriaRequest.put("entityId", currentUser.getId());
                    criteriaRequest.put("entityName", "LoginInformation");
                    criteriaRequest.put("ipAddress", currentUser.getLastLoginIp());
                    criteriaRequest.put("createdBy", currentUser);
                    loggingService.saveTransactionInformationLog(criteriaRequest);
                    basePayload = new ApiResponse(Boolean.TRUE, "Password change successful");
                    log.info("Password changed successfully");
                } else {
                    basePayload = new ApiResponse(Boolean.FALSE, "Invalid current password");
                    log.info("Invalid current password specified");
                }
            } else {
                basePayload = new ApiResponse(Boolean.FALSE, "Invalid new password");
                log.info("Password entries do not match");
            }
        }

        return ResponseEntity.ok(basePayload);
    }

    /**
     * This method is used by admin to change a user's password, it will send
     * notification email when the change is successful
     *
     * @param criteriaRequest - request json payload
     * @return - VdcnBasePayload response showing the outcome of the operation
     */
    @RequestMapping("/changeUserPassword")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional
    public ResponseEntity<?> changeUserPassword(@RequestBody Map<String, Object> criteriaRequest) {

        PayloadAbstractBase basePayload;
        UserPrincipal currentPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginInformation currentAdmin = loginInfoRepos.getOne(currentPrincipal.getId());
        log.info("{} initiated password change request with payload: {}", currentAdmin.getUsername(), criteriaRequest);

        if (criteriaRequest != null) {
            Number entityId = (Number) criteriaRequest.get("loginId");
            Optional<LoginInformation> optionalRequestUser = loginInfoRepos.findById(entityId.longValue());

            if (optionalRequestUser.isPresent()) {
                LoginInformation requestUser = loginInfoRepos.getOne(entityId.longValue());

                String new_password = ApplicationUtility.createRandomCode(7, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                requestUser.setPassword(passwordEncoder.encode(new_password));
                requestUser.setPwordResetRequired(Boolean.TRUE);

                String message = currentAdmin.getUsername() + " has initiated password reset on user's account.\n User's name is: "
                        + requestUser.getUsername() + ". Reason is: " + criteriaRequest.get("resetReason");
                String emailMessage = "Your password has been reset.\n Your new password is: " + new_password + ".\n Please login to your account now to change your "
                        + "password to one that you can easily remember";

                final DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                criteriaRequest.put("message", message);
                criteriaRequest.put("activityTime", dateTimeFormatter.format(new Date()));
                criteriaRequest.put("activity", ApplicationActivitiesEnum.CHANGEPASSWORD.toString());
                criteriaRequest.put("entityId", requestUser.getId());
                criteriaRequest.put("entityName", "LoginInformation");
                criteriaRequest.put("ipAddress", currentAdmin.getLastLoginIp());
                criteriaRequest.put("createdBy", currentAdmin);
                loggingService.saveTransactionInformationLog(criteriaRequest);
                Person person = requestUser.getLoginPerson();
                ContactInformation contact = person.getContactInformationList().get(0);
                loginInfoRepos.save(requestUser);

                externalIntCom.sendSecurityServiceEmailMessage(emailMessage, contact.getPrimaryEmailAddress(), "Password Changed");
                log.info("Sent new password via email: {}", new_password);
                basePayload = new ApiResponse(Boolean.TRUE, "Password has been changed successfully and email sent to user");
            } else {
                log.info("Could not fund user with given ID: {}", criteriaRequest.get("loginId"));
                basePayload = new ApiResponse(false, "Could not find user with given ID");
            }
        } else {
            log.info("One or more missing required params in payload");
            basePayload = new ApiResponse(false, "Invalid request param");
        }
        return ResponseEntity.ok(basePayload);
    }
}
