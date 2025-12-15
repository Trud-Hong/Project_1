package com.health.config;

import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthConfig {
    public static final String CLIENT_ID = "73594549715-rsmfnvea4v41kp5o2hi2j9vj38cmqcm8.apps.googleusercontent.com"; // 구글에서 발급받은 클라이언트 ID
    public static final String CLIENT_SECRET = "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"; // 구글에서 발급받은 클라이언트 시크릿
    public static final String REDIRECT_URI = "http://localhost:8080/heal/login/oauth2/code/google";

    public static final String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static final String TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    public static final String SCOPE = "openid email profile";
}

