package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;

import java.util.Set;

public class ProfileTestData {

    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class,"mailNotifications");

    public static final Set<String> WRONG_SET = Set.of("1", "3", "7");
    public static final String USER_PASSWORD = "password";
    public static final ContactTo USER_CONTACT_SKYPE = new ContactTo("skype","userSkype");
    public static final ContactTo USER_CONTACT_MOBILE = new ContactTo("mobile","+01234567890");
    public static final ContactTo USER_CONTACT_WEB = new ContactTo("website","user.com");
    public static final Set<ContactTo> USER_SET = Set.of(USER_CONTACT_SKYPE, USER_CONTACT_MOBILE, USER_CONTACT_WEB);

    public static final ContactTo ADMIN_CONTACT_GITHUB = new ContactTo("github","adminGitHub");
    public static final ContactTo ADMIN_CONTACT_TG = new ContactTo("tg","adminTg");
    public static final ContactTo ADMIN_CONTACT_VK = new ContactTo("vk","adminVk");
    public static final Set<ContactTo> ADMIN_SET = Set.of(ADMIN_CONTACT_GITHUB, ADMIN_CONTACT_TG, ADMIN_CONTACT_VK);
    public static final ProfileTo USER_PROFILETO = new ProfileTo(1L,null, USER_SET);
    public static final ProfileTo ADMIN_PROFILETO = new ProfileTo(2L,null, ADMIN_SET);
    public static final ProfileTo NEW_PROFILETO = new ProfileTo(3L,null, null);

    public static final String NOTIFICATION_THREE_DAYS_BEFORE_DEAD_LINE = "three_days_before_deadline";
    public static final String NOTIFICATION_TWO_DAYS_BEFORE_DEAD_LINE = "two_days_before_deadline";
    public static final String NOTIFICATION_DEADLINE = "deadline";
    public static final Set<String> NOTIFICATIONS_SET = Set.of(NOTIFICATION_THREE_DAYS_BEFORE_DEAD_LINE,
            NOTIFICATION_TWO_DAYS_BEFORE_DEAD_LINE, NOTIFICATION_DEADLINE);

    public static <T> String jsonWithPassword(T profileTo, String password) {
        return JsonUtil.writeAdditionProps(profileTo, "password", password);
    }
}
