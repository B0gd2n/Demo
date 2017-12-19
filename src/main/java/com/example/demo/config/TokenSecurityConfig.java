package com.example.demo.config;

import com.example.demo.security.EntryPointUnauthorizedHandler;
import com.example.demo.security.PreAuthenticatedTokenHeaderProcessingFilter;
import com.example.demo.security.TokenBasedUserDetailsService;
import com.example.demo.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${demo.token.header}")
    private String tokenHeader;
    @Autowired
    UserService userService;

    @Autowired
    private EntryPointUnauthorizedHandler unauthorizedHandler;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return userService;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenBasedUserDetailsService tokenBasedUserDetailsService() {
        return new TokenBasedUserDetailsService();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
        PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
        preauthAuthProvider.setPreAuthenticatedUserDetailsService(tokenBasedUserDetailsService());
        return preauthAuthProvider;
    }

    @Bean
    public PreAuthenticatedTokenHeaderProcessingFilter requestHeaderAuthenticationFilter(
            final AuthenticationManager authenticationManager) {
        PreAuthenticatedTokenHeaderProcessingFilter filter = new PreAuthenticatedTokenHeaderProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setExceptionIfHeaderMissing(false);
        filter.setPrincipalRequestHeader(tokenHeader);
        filter.setInvalidateSessionOnPrincipalChange(true);
        filter.setCheckForPrincipalChanges(true);
        filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return filter;
    }

    @Bean
    public FilterRegistrationBean registration(PreAuthenticatedTokenHeaderProcessingFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest()
                .authenticated();
        // @formatter:on

        // Custom JWT based authentication
        httpSecurity.addFilter(requestHeaderAuthenticationFilter(authenticationManager()));
    }
}
