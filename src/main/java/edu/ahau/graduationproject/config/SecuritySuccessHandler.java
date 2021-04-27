package edu.ahau.graduationproject.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created on 2021/2/6.
 *
 * @author Xun Zhang
 */
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        HttpSession session = request.getSession();

        for (GrantedAuthority g:
                 authorities) {
                if ("ROLE_student".equals(g.getAuthority())){
                    session.setAttribute("topper",g.getAuthority());
                    response.sendRedirect("/all/home");
                }
                if ("ROLE_teacher".equals(g.getAuthority())){
                    session.setAttribute("topper",g.getAuthority());
                    response.sendRedirect("/all/home");
                }
            }
        }
}
