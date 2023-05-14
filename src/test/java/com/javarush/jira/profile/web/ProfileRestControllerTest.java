package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.GUEST_MAIL;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.web.ProfileTestData.PROFILE_MATCHER;
import static com.javarush.jira.profile.web.ProfileTestData.USER_PASSWORD;
import static com.javarush.jira.profile.web.ProfileTestData.ADMIN_PROFILETO;
import static com.javarush.jira.profile.web.ProfileTestData.ADMIN_SET;
import static com.javarush.jira.profile.web.ProfileTestData.jsonWithPassword;
import static com.javarush.jira.profile.web.ProfileTestData.NEW_PROFILETO;
import static com.javarush.jira.profile.web.ProfileTestData.NOTIFICATIONS_SET;
import static com.javarush.jira.profile.web.ProfileTestData.USER_PROFILETO;
import static com.javarush.jira.profile.web.ProfileTestData.WRONG_SET;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUser()  throws Exception{
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(USER_PROFILETO));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAdmin()  throws Exception{
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(ADMIN_PROFILETO));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getNewProfile()  throws Exception{
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(NEW_PROFILETO));
    }
    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateMailNotifications() throws Exception {
        Profile profileBefore = profileRepository.getExisted(USER_PROFILETO.id());
        ProfileTo profileToBefore = profileMapper.toTo(profileBefore);
        profileToBefore.setMailNotifications(NOTIFICATIONS_SET);

        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(profileToBefore,USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profileAfter = profileRepository.getExisted(USER_PROFILETO.id());
        ProfileTo profileToAfter = profileMapper.toTo(profileAfter);
        Assertions.assertEquals(profileToAfter.getMailNotifications(), profileToBefore.getMailNotifications());
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateContacts() throws Exception {
        Profile profileBefore = profileRepository.getExisted(USER_PROFILETO.id());
        ProfileTo profileToBefore = profileMapper.toTo(profileBefore);
        profileToBefore.setContacts(ADMIN_SET);

        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(profileToBefore,USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profileAfter = profileRepository.getExisted(USER_PROFILETO.id());
        ProfileTo profileToAfter = profileMapper.toTo(profileAfter);
        Assertions.assertEquals(profileToAfter.getContacts(), profileToBefore.getContacts());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void InvalidUpdateValues() throws Exception {
        Profile invalid = profileRepository.getExisted(USER_PROFILETO.id());
        ProfileTo invalidTo = profileMapper.toTo(invalid);
        invalidTo.setMailNotifications(WRONG_SET);

        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(invalidTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
