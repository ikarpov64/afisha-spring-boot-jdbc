package org.javaacademy.afisha.util;

import lombok.Value;

@Value
public class UrlConstants {
    public static final String BASE_URI = "/api/v1";
    public static final String PLACE_URL = "/api/v1/place";
    public static final String PLACE_URL_VAR = "/api/v1/place/{id}";
    public static final String EVENT_URL = "/api/v1/event";
    public static final String EVENT_URL_VAR = "/api/v1/event/{id}";
    public static final String EVENT_NEW_URL = "/event/new";
    public static final String TICKET_URL = "/ticket";
    public static final String TICKET_URL_VAR = "/ticket/{id}";
    public static final String REPORT_URL = "/report";
    public static final String SALE_URL = "/sale/{id}";
}
