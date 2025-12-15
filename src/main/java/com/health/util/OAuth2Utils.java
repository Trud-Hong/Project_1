package com.health.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.health.config.GoogleOAuthConfig;

public class OAuth2Utils {

    // Access Token을 얻는 메서드
    public static String getAccessToken(String authorizationCode) throws IOException {
        String url = "https://oauth2.googleapis.com/token";
        
        String postData = "code=" + authorizationCode
                          + "&client_id=" + GoogleOAuthConfig.CLIENT_ID
                          + "&client_secret=" + GoogleOAuthConfig.CLIENT_SECRET
                          + "&redirect_uri=" + GoogleOAuthConfig.REDIRECT_URI
                          + "&grant_type=authorization_code";
        
        // 요청 보내기
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.getOutputStream().write(postData.getBytes("UTF-8"));

        // 응답 받기
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
    }

    // JSON 응답에서 Access Token 파싱
    public static String parseAccessToken(String response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("access_token").asText();
    }

    // 사용자 정보를 얻는 메서드
    public static String getUserInfo(String accessToken) throws Exception {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        URL url = new URL(userInfoUrl + "?access_token=" + accessToken);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}
