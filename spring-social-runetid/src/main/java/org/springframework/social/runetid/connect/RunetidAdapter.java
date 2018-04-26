/*
 * Copyright 2013-2016 the original author or authors.
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.runetid.api.Runetid;
import org.springframework.social.runetid.api.UserInfo;

/**
 * Runetid {@link ApiAdapter} implementation.
 *
 * @author vkolodrevskiy
 */
public class RunetidAdapter implements ApiAdapter<Runetid> {
    private final static Log log = LogFactory.getLog(RunetidAdapter.class);

    public RunetidAdapter() {
    }

    @Override
    public boolean test(Runetid runetid) {
        //TODO: implement
        return true;
    }

    @Override
    public void setConnectionValues(Runetid runetid, ConnectionValues values) {
        UserInfo userInfo = runetid.getUserInfo();
        values.setProviderUserId(String.valueOf(userInfo.getRunetId()));
        values.setDisplayName(userInfo.getFirstName() + " " + userInfo.getLastName());
        values.setProfileUrl("https://runet-id.com/" + userInfo.getRunetId() + "/");
        values.setImageUrl(userInfo.getPhoto().getLarge());
    }

    @Override
    public UserInfo fetchUserProfile(Runetid runetid) {
        return runetid.getUserInfo();
    }

    @Override
    public void updateStatus(Runetid runetid, String message) {
        // It's not good idea to post something.
    }
}
