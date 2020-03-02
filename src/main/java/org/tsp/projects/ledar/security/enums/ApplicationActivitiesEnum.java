package org.tsp.projects.ledar.security.enums;

public enum ApplicationActivitiesEnum {

    CHANGEPASSWORD("CHANGE USER'S PASSWORD"),
    MYPASSWORD("CHANGE OWN'S PASSWORD"),
    ACCOUNTLOGIN("APPLICATION USER LOGGED IN");

    private final String activity;

    ApplicationActivitiesEnum(String qualification) {
        this.activity = qualification;
    }

    @Override
    public String toString() {
        return this.activity;
    }
}
