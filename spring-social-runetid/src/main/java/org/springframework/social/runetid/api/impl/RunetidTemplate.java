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
package org.springframework.social.runetid.api.impl;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.runetid.api.Runetid;
import org.springframework.social.runetid.api.UserInfo;
import org.springframework.social.runetid.connect.RunetidAccessGrant;
import org.springframework.social.runetid.connect.RunetidOAuth2Template;

/**
 * {@link Runetid} implementation.
 *
 * @author vkolodrevskiy
 */
public class RunetidTemplate extends AbstractOAuth2ApiBinding implements Runetid {
    private final Integer providerUserId;
    private final String email;
    private final String accessToken;
    private final String clientId;
    private final String clientSecret;
    private final RunetidOAuth2Template template;


    public RunetidTemplate() {
        this.providerUserId = null;
        this.accessToken = null;
        this.clientId = null;
        this.clientSecret = null;
        this.email = null;
        this.template = null;
    }

    public RunetidTemplate(Integer providerUserId, String email, String accessToken, String clientId, String clientSecret) {
        super(accessToken);
        this.providerUserId = providerUserId;
        this.email = email;
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.template = new RunetidOAuth2Template(clientId, clientSecret);
    }

    @Override
    public UserInfo getUserInfo() {
        if (!isAuthorized()) {
            return null;
        }
        RunetidAccessGrant runetidAccessGrant = (RunetidAccessGrant) template.exchangeForAccess(accessToken, null, null);
        return runetidAccessGrant.getUserInfo();
    }
}
