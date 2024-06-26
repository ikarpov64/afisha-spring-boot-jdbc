package org.javaacademy.afisha.util;

import lombok.Value;

@Value
public class UrlConstants {
    public static final String PLACE_URL = "/api/v1/place";
    public static final String PLACE_URL_VAR = "/api/v1/place/{id}";
    public static final String EVENT_URL = "/api/v1/event";
    public static final String EVENT_URL_VAR = "/api/v1/event/{id}";
    public static final String REPORT_URL = "/api/v1/report";
    public static final String SALE_URL = "/api/v1/sale/{id}";
}
