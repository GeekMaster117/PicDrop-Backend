package com.geekmaster117.springweb.config;

import com.geekmaster117.springweb.constants.Urls;
import com.geekmaster117.springweb.services.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityChainConfig
{
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.cors(Customizer.withDefaults());

        http.csrf(csrf -> csrf.ignoringRequestMatchers("register"));

        http.exceptionHandling(exception -> {
            exception.defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    new NegatedRequestMatcher(new AntPathRequestMatcher("register")));
        });

        http.authorizeHttpRequests(request -> {
            request.requestMatchers("register").permitAll();
            request.anyRequest().authenticated();
        });

        http.formLogin(login -> login.defaultSuccessUrl(Urls.frontendUrl, true));

        http.oauth2Login(oauth2 -> {
            oauth2.defaultSuccessUrl(Urls.frontendUrl, true);
            oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
        });

        http.logout(Customizer.withDefaults());

        return http.build();
    }
}
