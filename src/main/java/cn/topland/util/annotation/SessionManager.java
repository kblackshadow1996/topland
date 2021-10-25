package cn.topland.util.annotation;

import cn.topland.entity.User;
import cn.topland.service.UserService;
import cn.topland.util.exception.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * session管理
 */
@Component
public class SessionManager {

    private static final String SESSION_USER = "user";

    @Autowired
    private UserService userService;

    public User getUser(HttpSession httpSession) throws QueryException {

        return hasUser(httpSession) ? userService.get(getAttribute(httpSession, SESSION_USER)) : null;
    }

    public void setUser(Long userId, HttpSession session) {

        session.setAttribute(SESSION_USER, userId);
    }

    private boolean hasUser(HttpSession httpSession) {

        return httpSession != null && httpSession.getAttribute(SESSION_USER) != null;
    }

    private Long getAttribute(HttpSession httpSession, String attributeName) {

        Object value = httpSession.getAttribute(attributeName);
        if (value instanceof String) {

            return Long.valueOf((String) value);
        } else if (value instanceof Long) {

            return (Long) value;
        } else {

            return null;
        }
    }

    public void removeUser(HttpSession session) {

        session.removeAttribute(SESSION_USER);
    }
}