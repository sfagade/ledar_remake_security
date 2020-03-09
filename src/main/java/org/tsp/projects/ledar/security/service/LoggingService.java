package org.tsp.projects.ledar.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tsp.projects.ledar.security.model.LoginInformation;
import org.tsp.projects.ledar.security.model.UsersLastActivities;
import org.tsp.projects.ledar.security.repository.UsersLastActivitiesRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class LoggingService {

    private final UsersLastActivitiesRepository userLastActivityRepos;

    @Autowired
    public LoggingService(UsersLastActivitiesRepository userLastActivityRepos) {
        this.userLastActivityRepos = userLastActivityRepos;
    }

    private final DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    /**
     * This method is used to log every actions from the clients to the database
     *
     * @param activityMap
     */
    public void saveTransactionInformationLog(Map<String, Object> activityMap) {

        UsersLastActivities userActivity;
        try {
            log.info("Logging user activity to database {}", activityMap);
            userActivity = new UsersLastActivities(null, activityMap.get("activity").toString(), dateTimeFormatter.parse(activityMap.get("activityTime").toString()),
                    activityMap.get("ipAddress").toString(), (Long) activityMap.get("entityId"), activityMap.get("entityName").toString(),
                    activityMap.get("longitude").toString(), activityMap.get("latitude").toString(), activityMap.get("client").toString(),
                    activityMap.get("message").toString(), (LoginInformation) activityMap.get("createdBy"), null, null);

            userLastActivityRepos.save(userActivity);

        } catch (ParseException ex) {
            log.error("Failed to log event to database", ex);
        }

    }
}
