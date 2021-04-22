package edu.ahau.graduationproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created on 2021/2/3.
 *
 * @author Xun Zhang
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable();


        //释放静态资源
        http.authorizeRequests()
                .antMatchers("/assets/css/**","/assets/js/**","/css/**,/js/**","/font/**","/images/**"
                        ,"/lay/**","layui.js","layui.all.js","favicon.ico","/webjars/jquery/3.4.1/jquery.min.js").permitAll()
                .antMatchers("student/**").permitAll();



        //首页,根据用户的角色重定向跳转
        http.formLogin().loginPage("/")
                .loginProcessingUrl("/pwd")
                .successHandler(new SecuritySuccessHandler())
                .failureForwardUrl("/fail");
//                .defaultSuccessUrl("/student/main");

        //设置退出
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        //用户权限使用
//        http.authorizeRequests().antMatchers("/student/test").hasRole("ROLE")

        //rememberMe
        http.rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rememberCookie")
                .tokenRepository(persistentTokenRepository);

    }
}
