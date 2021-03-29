package edu.ahau.graduationproject.utils;

import edu.ahau.graduationproject.domain.Student;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Created on 2021/2/5.
 *
 * @author Xun Zhang
 */
public class IDUtil {
    public static String getID(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String stuId = securityContextImpl.getAuthentication().getName();
        return stuId;
    }

    public static String getAuthor(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        Collection<? extends GrantedAuthority> authorities = securityContextImpl.getAuthentication().getAuthorities();

        String role = authorities.iterator().next().toString();

        String[] s = role.split("_");
        return s[1];

    }
}
