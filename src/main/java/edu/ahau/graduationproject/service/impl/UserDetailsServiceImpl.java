package edu.ahau.graduationproject.service.impl;

import edu.ahau.graduationproject.dto.UserDTO;
import edu.ahau.graduationproject.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username={}",username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDTO student = userMapper.selectStudentById(username);
        UserDTO teacher = userMapper.selectTeacherById(username);

        /*
         * 对User进行用户标记
         */
        if (student==null&&teacher==null){
            throw new UsernameNotFoundException("用户名或密码不正确，请重新输入");
        }
        //老师
        if (student==null) {
            List<GrantedAuthority> teacher1 = AuthorityUtils.createAuthorityList("ROLE_teacher");
            @SuppressWarnings("uncheck")
            User user = new User(username, encoder.encode(teacher.getPassword()),teacher1);
            return user;
        }
        //学生
        if (teacher==null) {
            List<GrantedAuthority> authUser = AuthorityUtils.createAuthorityList("ROLE_student");
            @SuppressWarnings("uncheck")
            User user1 = new User(username, encoder.encode(student.getPassword()),authUser);
            return user1;
        }
        return null;
    }
}
