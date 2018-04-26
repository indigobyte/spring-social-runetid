/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.runetid.connect;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.runetid.api.UserInfo;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Runetid-specific extension of {@link OAuth2Template}.
 *
 * @author vkolodrevskiy
 */
public class RunetidOAuth2Template extends OAuth2Template {
    public static final String OAUTH_URI = "https://runet-id.com/oauth/main/dialog";
    public static final String API_URI = "http://api.runet-id.com";
    private final String apiKey;
    private final String secret;
    private final String hash;

    public RunetidOAuth2Template(String clientId, String clientSecret) {
        super(clientId, clientSecret, OAUTH_URI, API_URI);
        apiKey = clientId;
        secret = clientSecret;
        hash = DigestUtils.md5DigestAsHex((apiKey + secret).getBytes(StandardCharsets.UTF_8));
        setUseParametersForClientAuthentication(true);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        StringBuilder authUrl = new StringBuilder(OAUTH_URI);
        authUrl.append("?apikey=");
        authUrl.append(apiKey);
        for (Map.Entry<String, List<String>> param : parameters.entrySet()) {
            String key = param.getKey();
            if (Objects.equals(key, "redirect_uri")) {
                key = "url";
            }
            String name = formEncode(key);
            for (String s : param.getValue()) {
                authUrl.append('&').append(name);
                if (StringUtils.hasLength(s)) {
                    authUrl.append('=').append(formEncode(s));
                }
            }
        }
        return authUrl.toString();
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //params.set("redirect_uri", redirectUri);
        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        params.put("token", Collections.singletonList(authorizationCode));
        HttpHeaders headers = new HttpHeaders();
        headers.add("ApiKey", apiKey);
        headers.add("Hash", hash);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        UserInfo userInfo = getRestTemplate().postForObject(API_URI + "/user/auth", entity, UserInfo.class);
        return createAccessGrant(authorizationCode, null, null, null, Collections.singletonMap("userInfo", userInfo));
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
                List<MediaType> types = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                types.add(MediaType.valueOf("text/json"));
                jacksonConverter.setSupportedMediaTypes(types);
            }
        }
        return restTemplate;
    }

    // When scope has "offline" option Runetid returns expires_in=0, setting it to null in this case
    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        return new RunetidAccessGrant(accessToken, (UserInfo) response.get("userInfo"));
    }
}
