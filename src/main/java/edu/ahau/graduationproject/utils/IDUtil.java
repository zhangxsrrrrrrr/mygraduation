package edu.ahau.graduationproject.utils;

import edu.ahau.graduationproject.domain.Student;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.http.HttpServletRequest;

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
}
