package org.tsp.projects.ledar.security.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.tsp.projects.ledar.security.payload.response.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ExternalIntegrationComponent {

    @Value("${ldr.config.discovery.outpostService}")
    private String outpostServiceHost;

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalIntegrationComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * The static method is used to push emails out of this service to
     * recipients via the outpost service
     *
     * @param message:      message to send as email
     * @param emailAddress: email address to send to
     * @param subject:      subject of the email
     */
    @Deprecated
    @Async
    public void sendSecurityServiceEmailMessage(String message, String emailAddress, String subject) {
        Map<String, String> emailParams = new HashMap<>();
        emailParams.put("emailMessage", message);
        emailParams.put("userEmail", emailAddress);
        emailParams.put("subject", subject);

        String url = outpostServiceHost + "/api/mailClientResource/sendApplicationSimpleEmailMessage";
        log.info("About to call outpost for Email");
        try {
            restTemplate.postForObject(url, emailParams, ApiResponse.class);
        } catch (HttpServerErrorException ex) {
            log.error("Error connecting to outpost", ex);
        }
    }

    /**
     * This method is used to send SMS messages via outpost service
     *
     * @param phoneNumbers phone number to send message to
     * @param message message to send to user
     */
    @Async
    public void sendCustomerRegistrationSms(ArrayList<String> phoneNumbers, String message) {
        Map<String, Object> smsParams = new HashMap<>();
        smsParams.put("message", message);
        smsParams.put("recipients", phoneNumbers);

        String url = outpostServiceHost + "/api/messageClientResource/sendSmsMessage";
        log.info("About to call outpost for SMS");
        try {
            restTemplate.postForObject(url, smsParams, ApiResponse.class);
        } catch (HttpServerErrorException ex) {
            log.error("Error connecting to outpost", ex);
        }
    }

    /**
     * This method is used to send Email message via outpost service using MailJet
     * template
     */
    @Async
    public void sendSecurityServiceEmailWithTemplate(Map<String, Object> messageMap) {
        log.info("About to call mailer on outpost with: {}", messageMap);
        if (messageMap != null && messageMap.containsKey("emailAddress") && messageMap.get("emailAddress") != null) {
            String url = outpostServiceHost + "/api/mailClientResource/customerAccountEmail";
            try {
                restTemplate.postForObject(url, messageMap, ApiResponse.class);
            } catch (HttpServerErrorException ex) {
                log.error("Error connecting to outpost", ex);
            }
        } else {
            log.info("Email address not specified");
        }
    }
}
