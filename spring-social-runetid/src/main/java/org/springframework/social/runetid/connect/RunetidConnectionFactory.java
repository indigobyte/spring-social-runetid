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

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.runetid.api.Runetid;

/**
 * Runetid {@link org.springframework.social.connect.ConnectionFactory} implementation.
 *
 * @author vkolodrevskiy
 */
public class RunetidConnectionFactory extends OAuth2ConnectionFactory<Runetid> {

    public RunetidConnectionFactory(String clientId, String clientSecret) {
        super("runetid", new RunetidServiceProvider(clientId, clientSecret), new RunetidAdapter());
    }

    @Override
    public boolean supportsStateParameter() {
        return false;
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return Integer.toString(((RunetidAccessGrant) accessGrant).getUserInfo().getRunetId());
    }
}
