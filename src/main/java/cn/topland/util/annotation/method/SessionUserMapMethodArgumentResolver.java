package cn.topland.util.annotation.method;

import cn.topland.entity.User;
import cn.topland.util.annotation.SessionManager;
import cn.topland.util.annotation.bind.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionUser注解解析器
 */
@Component
public class SessionUserMapMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private SessionManager manager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasMethodAnnotation(SessionUser.class)
                && parameter.getParameterType() == User.class;
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpSession httpSession = webRequest.getNativeRequest(HttpServletRequest.class).getSession();
        return manager.getUser(httpSession);
    }
}