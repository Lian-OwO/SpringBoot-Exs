package com.example.ch4test.config;

import com.example.ch4test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            //기본 인증 방식, 로그인 폼 방식을 이용. id/pw 이용하겠다
            // Bear, 토큰 방식, 예)jwt 많이 사용함. 서버에 부담 감소위해서.
            // 기본적으로, 로그인 기능과, 로그아웃 기능을 가지고 있음.
            // 만약, 개발자가 다른 로그인 폼을 이용한다면, 아래 설정 처럼 변경가능.
            //
        http.formLogin()
                // 기본적인 로그인 폼 양식을 따로 지정한 경우.
                .loginPage("/members/login")
                // 로그인 성공시 이동할 페이지.
                .defaultSuccessUrl("/")
                // 인증의 절차를, 멤버의 이메일 기준으로 설정.
                .usernameParameter("email")
                // 인증 실패시 이동할 페이지.
                .failureUrl("/members/login/error")
                .and()
                .logout()
                // 로그 아웃시 이용이 될 페이지 설정.
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                // 로그 아웃 성공시 이동할 페이지.
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
                // 인증 및 인가 상관없이 누구든지 접근이 가능한 페이지 설정.
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                // 인가가 관리자만 접근이 가능한 페이지 설정.
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // 그 외 모든 페이지는 인증이 되어야만 접근이 가능함.
                .anyRequest().authenticated()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

}