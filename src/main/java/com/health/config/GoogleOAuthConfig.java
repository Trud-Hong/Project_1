package com.health.config;

import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthConfig {
    public static final String CLIENT_ID = "..."; // 구글에서 발급받은 클라이언트 ID
    public static final String CLIENT_SECRET = "..."; // 구글에서 발급받은 클라이언트 시크릿
    public static final String REDIRECT_URI = "...";

    public static final String AUTH_URL = "...";
    public static final String TOKEN_URL = "...";
    public static final String USER_INFO_URL = "...";

    public static final String SCOPE = "...";
}

