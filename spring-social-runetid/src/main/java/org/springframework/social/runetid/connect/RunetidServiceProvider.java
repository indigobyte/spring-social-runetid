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

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.runetid.api.Runetid;
import org.springframework.social.runetid.api.impl.RunetidTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runetid {@link org.springframework.social.ServiceProvider} implementation.
 *
 * @author vkolodrevskiy
 */
public class RunetidServiceProvider extends AbstractOAuth2ServiceProvider<Runetid> {

    private final String clientSecret;
    private final String clientId;

    public RunetidServiceProvider(String clientId, String clientSecret) {
        super(new RunetidOAuth2Template(clientId, clientSecret));
        this.clientSecret = clientSecret;
        this.clientId = clientId;
    }

    @Override
    public Runetid getApi(String accessToken) {
        Integer providerUserId = null;
        Matcher idMatcher = Pattern.compile("\\[id=([^\\]]*)\\]").matcher(accessToken);
        if (idMatcher.find()) {
            providerUserId = Integer.valueOf(idMatcher.group(1));
            accessToken = accessToken.replaceAll("\\[id=" + providerUserId + "\\]", "");
        }

        String email = null;
        Matcher emailMatcher = Pattern.compile("\\[email=([^\\]]*)\\]").matcher(accessToken);
        if (emailMatcher.find()) {
            email = emailMatcher.group(1);
            accessToken = accessToken.replaceAll("\\[email=" + email + "\\]", "");
        }

        return new RunetidTemplate(providerUserId, email, accessToken, clientId, clientSecret);
    }
}
