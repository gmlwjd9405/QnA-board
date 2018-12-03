package com.hee.web;

import com.hee.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUSer(HttpSession session) {
        Object sessionedUSer = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUSer == null) {
            return false;
        }
        return true;
    }

    public static User getUSerFormSession(HttpSession session) {
        if (!isLoginUSer(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
