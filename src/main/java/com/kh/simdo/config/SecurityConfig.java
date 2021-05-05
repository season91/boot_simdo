package com.kh.simdo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 리멤버미 사용위해 설정
    private final DataSource dataSource;
    //private final MemberService memberService;

    public SecurityConfig(DataSource dataSource/*, MemberService memberService*/) {
        this.dataSource=dataSource;
        //this.memberService=memberService;
    }

    // 시큐리티 편하게 접근하기 위해
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 여기가 핵심.
        // 권한
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/index","/user/login", "/user/join", "/user/joinimpl/**","/user/idcheck").permitAll() //이경로들은 권한관리안하고도 쓸 수 있게
                .mvcMatchers(HttpMethod.POST, "/user/mailauth", "/mail", "/user/loginimpl" , "/user/joinimpl", "/user/idcheck").permitAll()
                //.mvcMatchers(HttpMethod.POST, "/board/upload").hasRole("MEMBER") 기본이 USER인데 다른등급으로 넣을거면 이렇게 지정하면된다. 어드민같이?
                .anyRequest().authenticated(); //위 경로 외에 모든요청은 막았다

        //세부설정
        //1.로그인페이지. 부트 기본로그인화면말고 우리가 만든 화면으로 지정한다
        // form login customizing
        http
                .formLogin()
                .loginPage("/user/login") // 부트 기본로그인화면말고 우리가 만든 화면으로 지정한다. default : login
                .loginProcessingUrl("/user/loginimpl")
                .usernameParameter("userEmail") // Spring security로 인증할때마다 principal로 사용할 값이 담긴 파라미터가 userId임을 알려준다. default : username. 그래서 셋팅할때 vo를 username으로 하면 편하다
                .defaultSuccessUrl("/index",true);

        //2. 로그아웃 관련 셋팅
        // logout
        http
                .logout()
                .logoutUrl("/user/logout") //로그아웃경로. Http method:post
                .logoutSuccessUrl("/"); //성공후 이동경로

        //3. csrf 열어주기. (메일관련 링크는 열어줘야한다).
        http.csrf().ignoringAntMatchers("/mail");

        //4. rememberme 로그인유지기능
       /* http.rememberMe()
                .userDetailsService(memberService)
                .tokenRepository(tokenRepository());*/
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // super.configure(web); 없어도됨

        // 정적자원에 대해서는 시큐리티 적용하지 않는다. *중요함! 안하면 css다 깨진다.
        web.ignoring()
                .mvcMatchers("/static/**") //모든경로 걸러준다
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 시큐리티 적용 안되게

    }
}
