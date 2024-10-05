package com.lezhintask.constant;

/**
 * API URI 정의
 */
public final class Path {
    public static final String API = "api";

    public static final String CONTENT = API + "/content";
    public static final String CONTENT_VIEW_HISTORY = CONTENT + "/view-history";
    public static final String TOP_VIEW_CONTENT = CONTENT + "/top-view";
    public static final String CONTENT_PURCHASE = CONTENT + "/purchase";

    public static final String AUTH = API + "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String SIGNUP = AUTH + "/signup";
}